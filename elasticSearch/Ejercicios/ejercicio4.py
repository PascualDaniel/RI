
from __future__ import print_function

import json
import pprint


from elasticsearch import Elasticsearch

import json

pp = pprint.PrettyPrinter(indent=2)
es = Elasticsearch()

importantWord = "nato"
# jlh gnd mutual_information, chi_square
metric = "gnd"

def consultaInicial(word):
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

def consultaFinal(tweets):
    results = es.search(
        index="tweets-20090624-20090626-en_es-10percent-v4",
        body={
            "size": 20,
            "query": {
                "more_like_this": {
                "fields":[ "text"] ,
                "like": tweets
                }
        }
        }, request_timeout=40)
    return results



def main():
    

    results = consultaInicial(importantWord)

    tweets =[]
    for term in results["hits"]["hits"]:
        dat =  {"_id": term["_id"]}
        
        tweets.append(dat)


    results2 = consultaFinal(tweets)
    file = open("RelatesTweetsEjercicio4.ndjson", "w")
    for term in results2["hits"]["hits"]:
        dat =  {"id": term["_id"], "created_at": term["_source"]["created_at"], "text": term["_source"]["text"]}
        file.write(json.dumps(dat))
        file.write("\n")
    print(results2)

    #jsonData = {"tematica": importantWord, "palabras": palabrasdata}
    #createFile(jsonData)

    #print(jsonData)


if __name__ == '__main__':
    main()
