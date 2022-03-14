
from __future__ import print_function

import json
import pprint


from elasticsearch import Elasticsearch

import json

pp = pprint.PrettyPrinter(indent=2)
es = Elasticsearch()

importantWord = "nato"
# jlh gnd mutual_information, chi_square
metric = "chi_square"
# Obtener un archivo con todos los tuits relativos a una temática especificada mediante una consulta
# y en un único idioma (inglés o castellano).
# Se incluirá identificador de usuario, fecha de creación y texto del tuit;
# puede usarse formato TSV o ndjson.


# Dicho archivo debe ser lo más exhaustivo posible por lo que la consulta inicial deberá ser expandida
# (es decir, se realizará una segunda consulta que añadirá a la consulta inicial nuevos términos);
# para ello se deberá usar la funcionalidad de términos significativos en agregaciones y, muy importante,
# también se deberán eliminar palabras vacías (inglés, español).
def consultaIncial(word):
    results = es.search(
        index="tweets-20090624-20090626-en_es-10percent-v4",
        body={
            "size": 0,
            "query": {
                "query_string": {
                    "query": "text:\""+word+"\" AND lang:en"
                }

            },
            "aggs": {
                "Terminos": {
                    "significant_terms": {
                        "field": "text",
                        "size": 20,
                        metric: {}
                    }
                }
            }
        }, request_timeout=40)
    return results


def consultaFinal(word):
    results = es.search(
        index="tweets-20090624-20090626-en_es-10percent",
        body={
            "size": 20,
            "query": {
                "bool": {
                    "should": {
                        "match": {
                            "text": word
                        }
                    }
                }
            }
        }, request_timeout=40)

    return results

import ndjson

def createFile(data):
    file = open("RelatesTweets.ndjson", "w")
    jason = json.dumps(data, indent=2)
  #  jason2 = ndjson.loads(data)
    output = ndjson.loads(jason)
    output2 = ndjson.dumps(output)
    print(output2)
    file.write(jason)
        
    file.close()


# Por ejemplo, si la temática escogida fueran las protestas en Irán se podría emplear como
#  consulta inicial (suponiendo una consulta query_string) “lang:en AND text:iran” y se obtendrían términos
# asociados como “iranelection”, “democracy”, “neda” o “tehran” (que son relevantes) pero también otros como “obama”,
#  “employment” o “badcredit” (que lo son menos o son simplemente spam que aprovechó el “trending topic”).
# Cada estudiante debe elegir su temática (véase) así como el número máximo de términos asociados a utilizar
# (no se pueden escoger manualmente los términos para la expansión).Debe razonarse la elección de la consulta
# base para la temática escogida, además de reflexionar críticamente sobre los resultados obtenidos con distintas
#  métricas y distintos números de términos.


def main():
    palabrasIniciales = set()

    results = consultaIncial(importantWord)
    for term in results["aggregations"]["Terminos"]["buckets"]:
        palabrasIniciales.add(term["key"].replace("_", "").strip())

    #palabrasdata = []
    file = open("RelatesTweets"+metric+".ndjson", "w")
    for palabra in palabrasIniciales:
        result2 = consultaFinal(palabra)
        #tweets = set()
        #tdata = []
        for term in result2["hits"]["hits"]:
            #tweets.add(term["_source"]["text"].replace("_", "").strip())
            dat =  {"id": term["_id"], "created_at": term["_source"]["created_at"], "text": term["_source"]["text"]}
            file.write(json.dumps(dat))
            file.write("\n")
            #tdata.append(dat)
        #palabrasdata.append({"palabra": palabra, "tweets": tdata})    




    #jsonData = {"tematica": importantWord, "palabras": palabrasdata}
    #createFile(jsonData)

    #print(jsonData)


if __name__ == '__main__':
    main()
