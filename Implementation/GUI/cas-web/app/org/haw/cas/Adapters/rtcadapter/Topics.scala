package org.haw.cas.Adapters.rtcadapter

object Topics extends Enumeration{

  type Topic = Value 
  val broadcast,needs,crevasses,riverstages,timelines,userPositions,posts = Value
  
  def contains(s:String):Boolean = {
    for (v <- values){
      if (v.toString() == s)
        return true     
    }
    return false
  }
}
