package com.kylerjackson.words

import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.kylerjackson.words.ui.main.MainViewModel

class WordsRepository(){
    /*
        This API used is the Oxford Languages API
     */
    val baseUrl = "https://od-api.oxforddictionaries.com/api/v2"
    val key = "40f68ceebcd3ac6916d92caa600692ab"
    val id = "62e27407"
    lateinit var requestQueue: RequestQueue
    lateinit var viewModel: MainViewModel

    fun setMainViewModel(vm:MainViewModel){
        viewModel = vm
    }

    fun getData(word:String){
        removeInflection(word)
    }

    //this is an API call to the Lemmas endpoint to remove any inflictions of a word. ex. Running -> Run
    //This must be called before getting the data of a word.
    fun removeInflection(word:String): String{
        var lemmasURL = baseUrl+"/lemmas/en-us/"+word.toLowerCase()
        var returnedWord = word

        var rootWordRequest = object: JsonObjectRequest(Method.GET, lemmasURL, null,{response->
            /*
                The path of the JsonObject to retrieve the root word is as follows:
                response/lexicalEntries/inflectionOf/id
             */

            var base_word = response.getJSONArray("results").getJSONObject(0).getJSONArray("lexicalEntries").getJSONObject(0).getJSONArray("inflectionOf").getJSONObject(0).getString("id")
            returnedWord = base_word

            //Once we get the root of the word we can use that to get the definitions and etymologies of the word.
            getWordData(returnedWord)
        },{error->

        }){
            //This overridden function replaces the headers with our ID and Key
            override fun getHeaders():MutableMap<String,String>{
                val headers = HashMap<String,String>()
                headers["app_id"] = id
                headers["app_key"] = key

                return headers
            }
        }
        requestQueue.add(rootWordRequest)
        return returnedWord
    }


    //This is called once the removeInflection function returns the root word. Always remove inflections before using the entries endpoint.
    fun getWordData(word:String){

        this.viewModel.word.value = word.capitalize()

        /*
            Full list of filters:
                definitions
                etymologies
                examples
                pronunciations
                regions
                registers
                variantForms
                domains
         */
        var filters = "definitions,etymologies"

        var url = baseUrl+"/entries/en-us/"+word.toLowerCase()+"?fields="+filters
        var definitions = ArrayList<String>()
        var etymologies = ArrayList<String>()

        var wordDataRequest = object: JsonObjectRequest(Method.GET,url,null,{response->
            var results = response.getJSONArray("results")

            //The result contains json objects for each usage of a word. Ex. Bear(an action) and Bear(an animal)
            for(i in 0 until results.length()) {

                //obj is the JSON object that contains the data of the found word usage.
                var obj = results.getJSONObject(i)

                /*
                    The path used to get the definition is as follows:
                        obj/lexicalEntries/entries/senses/definitions/[index 0]
                        [index 0] is the most common definition for the word

                    The path used to get the etymology is as follows:
                        obj/lexicalEntries/entries/etymologies/[index 0]
                 */

                /* obj/lexicalEntries */
                var lexicalEntriesObject = obj.getJSONArray("lexicalEntries").getJSONObject(0)

                /* obj/lexicalEntries/entries */
                var entriesObject = lexicalEntriesObject.getJSONArray("entries").getJSONObject(0)

                /* obj/lexicalEntries/entries/senses */
                var senses = entriesObject.getJSONArray("senses")

                /* obj/lexicalEntries/entries/senses/definitions[0] */
                var definition = senses.getJSONObject(0).getJSONArray("definitions").getString(0)
                definitions.add(definition)

                //Checks if there is a recorded etymology for the word as the program will crash if none are found
                if(entriesObject.has("etymologies")){

                    /* obj/lexicalEntries/entries/etymologies[0] */
                    var etymology = entriesObject.getJSONArray("etymologies").getString(0)
                    etymologies.add(etymology)
                }else{
                    etymologies.add("No etymology found for definition.")
                }
            }

            //sets the viewModel data to the found definitions and etymologies
            viewModel.definition.value = definitions
            viewModel.etymologies.value = etymologies

        },{error->

            //Usually the error is because of an incorrect spelling
            viewModel.word.value = "Incorrect Spelling!"
        }){
            //Replaces default headers with our id and key for the api
            override fun getHeaders():MutableMap<String,String>{
                val headers = HashMap<String,String>()
                headers["app_id"] = id
                headers["app_key"] = key
                return headers
            }
        }
        requestQueue.add(wordDataRequest)
    }

}
