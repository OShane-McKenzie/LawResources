package com.litecodez.lawresources

import android.content.Context

object ContentLoader {
    private val apis = contentRepository.apis()
    fun loadContent(context: Context, callBack:()->Unit={}){
        if(contentRepository.contentReady.value!=null){
            if(contentRepository.contentReady.value!!){
                contentRepository.getPolicies(apis){
                        success,message->
                    if(success){
                        contentRepository.getCredits(apis){
                                success1,message1->
                            if(success1){
                                contentRepository.getLaws(apis){
                                        success2,message2->
                                    if(success2){
                                        contentRepository.getFines(apis){
                                                success3,message3->
                                            if(success3){
                                                contentRepository.getMessages(apis){
                                                        success4,message4->
                                                    if(success4){
                                                        contentRepository.getSamples(apis){
                                                                success5,message5->
                                                            if(success5){
                                                                contentRepository.getWordings(apis){
                                                                        success6,message6->
                                                                    if(success6){
                                                                        contentRepository.getContacts(apis){
                                                                                success7,message7->
                                                                            if(success7){
                                                                                contentRepository.getValues(apis){
                                                                                        success8,message8->
                                                                                    if(success8){
                                                                                        contentRepository.getVideos(apis){
                                                                                                success9,message9->
                                                                                            if(success9){
                                                                                                contentRepository.getFeedbackCredential(apis){
                                                                                                        success10,message10->
                                                                                                    if(success10){
                                                                                                        callBack()
                                                                                                    }else{
                                                                                                        getToast(context,message10,long = true)
                                                                                                    }
                                                                                                }
                                                                                            }else{
                                                                                                getToast(context,message9,long = true)
                                                                                            }
                                                                                        }
                                                                                    }else{
                                                                                        getToast(context,message8,long = true)
                                                                                    }
                                                                                }
                                                                            }else{
                                                                                getToast(context,message7,long = true)
                                                                            }
                                                                        }
                                                                    }else{
                                                                        getToast(context,message6,long = true)
                                                                    }
                                                                }
                                                            }else{
                                                                getToast(context,message5,long = true)
                                                            }
                                                        }
                                                    }else{
                                                        getToast(context,message4,long = true)
                                                    }
                                                }
                                            }else{
                                                getToast(context,message3,long = true)
                                            }
                                        }
                                    }else{
                                        getToast(context,message2,long = true)
                                    }
                                }
                            }else{
                                getToast(context,message1,long = true)
                            }
                        }
                    }else{
                        getToast(context,message,long = true)
                    }
                }
            }else{
                getToast(context,"Unknown error loading content",long = true)
            }
        }else{
            getToast(context,"No data found",long = false)
        }
    }
}