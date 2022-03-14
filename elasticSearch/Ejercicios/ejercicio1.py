from __future__ import print_function

import datetime


from elasticsearch import Elasticsearch


es = Elasticsearch()

numberOfTT = 10
lenguage = "en"
# jlh gnd mutual_information, chi_square
metric = "gnd"


#  Escribir un script que genere un listado de “trending topics” para cada hora del dataset.
#  El script debe poder parametrizarse
#  (no hace falta que sea en línea de órdenes, puede hacerse cambiando el valor de variables en el código)
#  de tal manera que se pueda modificar el número de “trending topics”,
#  el idioma y la métrica a usar para generar los términos significativos.

# Se debe utilizar una consulta bool que obligue (must) dos condiciones:
# una sobre el rango del campo created_at de los tuits y
# otra con un query_string que indique el idioma del campo lang de los tuits.
# Puesto que el campo must de la consulta bool tendrá dos consultas internamente deberán
# indicarse con un array (es decir, entre corchetes).

# La consulta range, a su vez, recibirá dos valores:
#  uno para el campo gte y otro para el campo lte.
#  Para indicar esos valores hay dos opciones: usar el formato establecido
#  en el mapping del índice (es decir, EEE MMM dd HH:mm:ss Z yyyy)
# o bien usando otro formato (p.ej., un valor numérico de timestamp como 1245963600)
# e indicando dicho formato en la propia consulta con el campo format (p.ej., epoch_second).

#"Wed Jun 24 00:00:00 +0000 2009"
def getTrendingTopics(hour,hour2):
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
                    },{
                         "query_string":{
                            "query":"lang:"+lenguage
                        }
                    }]
                }
            }, "aggs": {
                "Terminos significativos": {
                    "significant_terms": {
                        "field": "text",       
                        metric : {},
                        "size": numberOfTT

                    }
                }

            }
        }
    )
    return results



def createFile(data):
    with open("trendingTopics.txt", "w") as f:
        for line in data:
            f.write(line + "\n")

from datetime import datetime, timedelta, timezone
def main():
    
    trendingTopics = []
    firstHour = datetime(2009, 6, 24,0, 0, 0, 0,timezone(timedelta(hours=0)))
    lastHour = datetime(2009, 6, 27,0, 0, 0, 0,timezone(timedelta(hours=0)))

    hora = timedelta(hours=1)

    while firstHour<lastHour:
        #"Wed Jun 24 00:00:00 +0000 2009"
        hour = firstHour.strftime("%a %b %d %H:%M:%S %z %Y")
        hour2 = (firstHour+hora).strftime("%a %b %d %H:%M:%S %z %Y")
        res = getTrendingTopics(hour,hour2)
        for re in  res["aggregations"]["Terminos significativos"]["buckets"]:
            trendingTopics.append(
               re["key"].replace("_", "").strip())

        firstHour += hora

    createFile(trendingTopics)    


if __name__ == '__main__':
    main()
