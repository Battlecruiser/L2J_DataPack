# Made by Mr. Have fun! Version 0.2
# rewritten by Rolarga, Version 0.3
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

RECOMMENDATION_OF_BALANKI_ID = 2864
RECOMMENDATION_OF_FILAUR_ID = 2865
RECOMMENDATION_OF_ARIN_ID = 2866
MARK_OF_MAESTRO_ID = 2867
LETTER_OF_SOLDER_DETACHMENT_ID = 2868
PAINT_OF_KAMURU_ID = 2869
NECKLACE_OF_KAMURU_ID = 2870
PAINT_OF_TELEPORT_DEVICE_ID = 2871
TELEPORT_DEVICE_ID = 2872
ARCHITECTURE_OF_KRUMA_ID = 2873
REPORT_OF_KRUMA_ID = 2874
INGREDIENTS_OF_ANTIDOTE_ID = 2875
WEIRD_BEES_NEEDLE_ID = 2876
MARSH_SPIDERS_WEB_ID = 2877
BLOOD_OF_LEECH_ID = 2878
BERNARDS_INTRODUCTION_ID = 3294
LETTER_OF_HAMIL3_ID = 3297
HUNTERS_RUNE2_ID = 3299
MARK_OF_SAGITTARIUS_ID = 3293
CRESCENT_MOON_BOW_ID = 3028
TALISMAN_OF_KADESH_ID = 3300
BLOOD_OF_LIZARDMAN_ID = 3306
LETTER_OF_HAMIL1_ID = 3295
LETTER_OF_HAMIL2_ID = 3296
HUNTERS_RUNE1_ID = 3298
TALISMAN_OF_SNAKE_ID = 3301
MITHRIL_CLIP_ID = 3302
STAKATO_CHITIN_ID = 3303
ST_BOWSTRING_ID = 3304
MANASHENS_HORN_ID = 3305
WOODEN_ARROW_ID = 17

#This adds all Info to a Mobs ->npcId:(step,dropcond,maxcount,chance,item)
HUNTERS = (3,1,10,50,HUNTERS_RUNE1_ID)
LETO=(13,2,141,100,BLOOD_OF_LIZARDMAN_ID)

DROPLIST={
   79:HUNTERS,
   80:HUNTERS,
   81:HUNTERS,
   82:HUNTERS,
   84:HUNTERS,
   86:HUNTERS,
   89:HUNTERS,
   90:HUNTERS,
   578:LETO,
   577:LETO,
   579:LETO,
   580:LETO,
   581:LETO,
   582:LETO,
   269:(6,1,10,50,HUNTERS_RUNE2_ID),
   270:(6,1,10,60,HUNTERS_RUNE2_ID),
   5090:(13,3,1,100,TALISMAN_OF_KADESH_ID),
   230:(10,4,1,10,STAKATO_CHITIN_ID),
   232:(10,4,1,10,STAKATO_CHITIN_ID),
   234:(10,4,1,10,STAKATO_CHITIN_ID),
   563:(10,5,1,10,MANASHENS_HORN_ID),
   233:(10,5,1,10,ST_BOWSTRING_ID),
   551:(10,5,1,10,MITHRIL_CLIP_ID)
}
def giveMiddle(st,itemid,step):
  st.giveItems(itemid,1)
  st.playSound("Itemsound.quest_middle")
  st.set("step",str(step+1))
  return

def giveNormal(st,itemid):
  st.giveItems(itemid,1)
  st.playSound("Itemsound.quest_itemget")
  return


class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
       htmltext = "7702-04.htm"
       st.set("cond","1")
       st.set("step","1")
       st.setState(STARTED)
       st.playSound("ItemSound.quest_accept")
       st.giveItems(BERNARDS_INTRODUCTION_ID,1)
    elif event == "7626_1" :
          htmltext = "7626-02.htm"
    elif event == "7626_2" :
          htmltext = "7626-03.htm"
          st.giveItems(LETTER_OF_HAMIL1_ID,1)
          st.takeItems(BERNARDS_INTRODUCTION_ID,1)
          st.set("step","2")
    elif event == "7626_3" :
          htmltext = "7626-06.htm"
    elif event == "7626_4" :
          htmltext = "7626-07.htm"
          st.giveItems(LETTER_OF_HAMIL2_ID,1)
          st.takeItems(HUNTERS_RUNE1_ID,10)
          st.set("step","5")
    elif event == "7653_1" :
          htmltext = "7653-02.htm"
          st.takeItems(LETTER_OF_HAMIL1_ID,1)
          st.set("step","3")
    elif event == "7514_1" :
          htmltext = "7514-02.htm"
          st.takeItems(LETTER_OF_HAMIL2_ID,1)
          st.set("step","6")
    return htmltext


 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   step = st.getInt("step")
   onlyone = st.getInt("onlyone")
   if id == CREATED :
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("step","0")
   if npcId == 7702 and step==0 and onlyone==0 :
        if (st.getPlayer().getClassId().getId() == 0x07 or st.getPlayer().getClassId().getId() == 0x16 or st.getPlayer().getClassId().getId() == 0x23) and st.getPlayer().getLevel() >= 39 :
          htmltext = "7702-03.htm"
        elif st.getPlayer().getClassId().getId() == 0x07 or st.getPlayer().getClassId().getId() == 0x16 or st.getPlayer().getClassId().getId() == 0x23 :
          htmltext = "7702-01.htm"
          st.exitQuest(1)
        else:
          htmltext = "7702-02.htm"
          st.exitQuest(1)
   elif npcId == 7702 and onlyone==1 :
      htmltext = "<html><head><body>This quest has already been completed.</body></html>"
   elif npcId == 7702 and step==1 and st.getQuestItemsCount(BERNARDS_INTRODUCTION_ID) :
      htmltext = "7702-05.htm"
   elif npcId == 7626 and step==1 and st.getQuestItemsCount(BERNARDS_INTRODUCTION_ID) :
      htmltext = "7626-01.htm"
   elif npcId == 7626 and step==2 and st.getQuestItemsCount(LETTER_OF_HAMIL1_ID) :
      htmltext = "7626-04.htm"
   elif npcId == 7626 and step==4 and st.getQuestItemsCount(HUNTERS_RUNE1_ID)==10 :
      htmltext = "7626-05.htm"
   elif npcId == 7626 and step==5 and st.getQuestItemsCount(LETTER_OF_HAMIL2_ID) :
      htmltext = "7626-08.htm"
   elif npcId == 7626 and step==8 :
      htmltext = "7626-09.htm"
      st.giveItems(LETTER_OF_HAMIL3_ID,1)
      st.set("step","9")
   elif npcId == 7626 and step==9 and st.getQuestItemsCount(LETTER_OF_HAMIL3_ID) :
      htmltext = "7626-10.htm"
   elif npcId == 7626 and step==12 and st.getQuestItemsCount(CRESCENT_MOON_BOW_ID) :
      htmltext = "7626-11.htm"
      st.set("step","13")
   elif npcId == 7626 and step==13 :
      htmltext = "7626-12.htm"
   elif npcId == 7626 and step==14 :
      htmltext = "7626-13.htm"
      st.giveItems(MARK_OF_SAGITTARIUS_ID,1)
      st.takeItems(CRESCENT_MOON_BOW_ID,1)
      st.takeItems(TALISMAN_OF_KADESH_ID,1)
      st.takeItems(BLOOD_OF_LIZARDMAN_ID,st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID))
      st.addExpAndSp(54726,20250)
      st.unset("step")
      st.set("cond","0")
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
      st.set("onlyone","1")
   elif npcId == 7653 and step==2 and st.getQuestItemsCount(LETTER_OF_HAMIL1_ID) :
      htmltext = "7653-01.htm"
   elif npcId == 7653 and step==3 :
      htmltext = "7653-03.htm"
   elif npcId == 7514 and step==5 and st.getQuestItemsCount(LETTER_OF_HAMIL2_ID) :
      htmltext = "7514-01.htm"
   elif npcId == 7514 and step==6 :
      htmltext = "7514-03.htm"
   elif npcId == 7514 and step==7 and st.getQuestItemsCount(TALISMAN_OF_SNAKE_ID) :
      htmltext = "7514-04.htm"
      st.takeItems(TALISMAN_OF_SNAKE_ID,1)
      st.set("step","8")
   elif npcId == 7514 and step==8 :
      htmltext = "7514-05.htm"
   elif npcId == 7717 and step==9 and st.getQuestItemsCount(LETTER_OF_HAMIL3_ID) :
      htmltext = "7717-01.htm"
      st.takeItems(LETTER_OF_HAMIL3_ID,1)
      st.set("step","10")
   elif npcId == 7717 and step==10 :
      htmltext = "7717-03.htm"
   elif npcId == 7717 and step==12 :
      htmltext = "7717-04.htm"
   elif npcId == 7717 and step==11 and st.getQuestItemsCount(STAKATO_CHITIN_ID) and st.getQuestItemsCount(MITHRIL_CLIP_ID) and st.getQuestItemsCount(ST_BOWSTRING_ID) and st.getQuestItemsCount(MANASHENS_HORN_ID) :
      htmltext = "7717-02.htm"
      st.takeItems(MITHRIL_CLIP_ID,1)
      st.takeItems(STAKATO_CHITIN_ID,1)
      st.takeItems(ST_BOWSTRING_ID,1)
      st.takeItems(MANASHENS_HORN_ID,1)
      st.giveItems(CRESCENT_MOON_BOW_ID,1)
      st.giveItems(WOODEN_ARROW_ID,10)
      st.set("step","12")
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   step, dropcondition, maxcount, chance, itemid = DROPLIST[npcId]
   random = st.getRandom(100)
   
   if st.getInt("step") == step and st.getQuestItemsCount(itemid)<maxcount and random < chance:
    if dropcondition == 1:
     if st.getQuestItemsCount(itemid)== maxcount-1 : 
      giveMiddle(st,itemid,step)
      if npcId==269 or npcId == 270:
       st.giveItems(TALISMAN_OF_SNAKE_ID,1)
       st.takeItems(HUNTERS_RUNE2_ID,10)
     else:
      giveNormal(st,itemid)
    elif dropcondition == 2 :
     if ((st.getQuestItemsCount(itemid)-120)*5)> st.getRandom(100) :
      st.getPcSpawn().addSpawn(5090,75315,40138,-3204)
      return "Serpent Demon Kadesh has spawned at X=75315 Y=40138 Z=-3204"
      st.takeItems(itemid, st.getQuestItemsCount(itemid))
      st.playSound("Itemsound.quest_before_battle")
     else:
      giveNormal(st,itemid)
    elif dropcondition == 3 :
     if st.getItemEquipped(7)==CRESCENT_MOON_BOW_ID:
      giveMiddle(st,itemid,step)
     else:
      st.getPcSpawn().addSpawn(5090)
    elif dropcondition == 4 :
     if st.getQuestItemsCount(MITHRIL_CLIP_ID) and st.getQuestItemsCount(ST_BOWSTRING_ID) and st.getQuestItemsCount(MANASHENS_HORN_ID) : 
      giveMiddle(st,itemid,step)
     else:
      giveNormal(st,itemid)
    elif dropcondition == 5:
     if st.getQuestItemsCount(MITHRIL_CLIP_ID) and st.getQuestItemsCount(ST_BOWSTRING_ID) and st.getQuestItemsCount(STAKATO_CHITIN_ID) :
      giveMiddle(st,itemid,step)
     elif st.getQuestItemsCount(MITHRIL_CLIP_ID) and st.getQuestItemsCount(MANASHENS_HORN_ID) and st.getQuestItemsCount(STAKATO_CHITIN_ID) :
      giveMiddle(st,itemid,step)
     elif st.getQuestItemsCount(ST_BOWSTRING_ID) and st.getQuestItemsCount(MANASHENS_HORN_ID) and st.getQuestItemsCount(STAKATO_CHITIN_ID) :
      giveMiddle(st,itemid,step)
     else:
      giveNormal(st,itemid)
   return
   

  
QUEST       = Quest(224,"224_TestOfSagittarius","Test Of Sagittarius")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7702)

CREATED.addTalkId(7702)
COMPLETED.addTalkId(7702)

for npcId in [7514,7626,7653,7702,7717]:
    STARTED.addTalkId(npcId)

for mobId in [230,232,233,234,269,270,5090,551,563,577,578,579,580,581,582,79,80,81,82,84,86,89,90]:
    STARTED.addKillId(mobId)

print "importing quests: 224: Test Of Sagittarius"
