from __future__ import print_function


import json

import datetime


from elasticsearch import Elasticsearch


es = Elasticsearch()

lenguage = "en"
# jlh gnd mutual_information, chi_square
metric = "gnd"

# Generar un índice de los tuits escritos en inglés con shingles de uno,dos y tres términos.


# Modificar el script del ejercicio anterior para que genere listados de 50 “trending topics”
# por cada hora del dataset.
# Modificar el script del ejercicio anterior para que genere listados de 50 “trending topics”


def getTrendingTopics(hour, hour2):
    results = es.search(
        index="tweets-20090624-20090626-en_es-10percent-v4",
        body={
            "size": 0,
            "query": {
                "bool": {
                    "must": [{
                        "range": {
                            "created_at": {
                                "gte": hour,
                                "lte": hour2
                            }
                        }
                    }, {
                        "query_string": {
                            "query": "lang:en"
                        }

                    }]
                }
            }, "aggs": {
                "Terminos significativos": {
                    "significant_terms": {
                        "field": "text",
                        metric : {},
                        "size": 50
                    }
                }

            }
        }
    , request_timeout=30)
    return results


# Una vez obtenidos los “trending topics” de cada hora procesarlos todos para localizar entidades
# en Wikidata asociadas a los mismos. Por ejemplo, si se encontraran los términos “michael jackson”,
# “cardiac arrest”, “heart attack” y “farrah fawcett” podrían asociarse, respectivamente,
# con las entidades Q2831, Q202837, Q12152 y Q102341.¡Atención! A fin de no “vapulear” al servidor de Wikidata
# hay que asegurarse de no realizar consultas para un término ya consultado con anterioridad.


def createFile(data):
    file = open("trendingTopicsID.txt", "w")
    for i in range(len(data)):
        file.write(str(data[i]) + "\n")
    file.close()


# usign apis rest get the wikidata id
def getWikidataId(term):
    url = "https://www.wikidata.org/w/api.php?action=wbsearchentities&search=" + \
        term + "&language=en&format=json"
    import requests
    response = requests.get(url)
    data = json.loads(response.text)
    ids = []
    for i in data['search']:
        ids.append(i['id'])
    return ids



from datetime import datetime, timedelta, timezone
def main():

    trendingTopics = set()
    firstHour = datetime(2009, 6, 24, 0, 0, 0, 0, timezone(timedelta(hours=0)))
    lastHour = datetime(2009, 6, 27, 0, 0, 0, 0, timezone(timedelta(hours=0)))

    hora = timedelta(hours=1)

    while firstHour < lastHour:
        #"Wed Jun 24 00:00:00 +0000 2009"
        hour = firstHour.strftime("%a %b %d %H:%M:%S %z %Y")
        hour2 = (firstHour+hora).strftime("%a %b %d %H:%M:%S %z %Y")
        res = getTrendingTopics(hour, hour2)
        print(res)
        for re in  res["aggregations"]["Terminos significativos"]["buckets"]:
            trendingTopics.add(
               re["key"].replace("_", "").strip().encode("UTF-8"))
        firstHour += hora

    # create a set of the ids elements
    from collections import OrderedDict
    trendingTopics = list(trendingTopics)
    ids = []
    for i in range(len(trendingTopics)):
        ids.append(trendingTopics[i])
        ids.append(getWikidataId(trendingTopics[i]))

    createFile(ids)


if __name__ == '__main__':
    main()
