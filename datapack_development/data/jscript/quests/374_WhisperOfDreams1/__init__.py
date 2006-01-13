# Whisper of Dreams, part 1 version 0.1 
# by DrLecter
print "importing quests:",
import sys
from net.sf.l2j import Config
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
#Quest info
QUEST_NUMBER,QUEST_NAME,QUEST_DESCRIPTION = 374,"WhisperOfDreams1","Whisper of Dreams, part 1"

#Variables
#Quest items drop rate
DROP_RATE   = 20
DROP_MAX = 100 #in % unless you change this
#Mysterious Stone drop rate
DROP_RATE_2 = 1
DROP_MAX_2 = 250 # default: ~ 1/250
#This setting alters the original price for fabrics.
#By default it gathers server's adena drop multiplier
ADENA_X=int(Config.RATE_DROP_ADENA)
#Rewards
SHOP_LIST={
5485:["etc_leather_yellow_i00",4,11200,"Sealed Tallum Tunic Textures"    ],# 4xTallum Tunic Textures: 11200a
5486:["etc_leather_gray_i00",  3,13700,"Sealed Dark Crystal Robe Fabrics"],
5487:["etc_leather_gray_i00",  2,18800,"Sealed Robe of Nightmare Fabrics"],
5488:["etc_leather_gray_i00",  2,18800,"Sealed Majestic Robe Frabrics"   ],
5489:["etc_leather_gray_i00",  6,16300,"Sealed Tallum Stockings Fabrics"] 
}

#Quest items
CB_TOOTH, DW_LIGHT, SEALD_MSTONE, MSTONE = range(5884,5888)
#Messages
default   = "<html><head><body>I have nothing to say to you.</body></html>"

#NPCs
MANAKIA,TORAI = 7515, 7557

#Mobs & Drop
DROPLIST = {620:[CB_TOOTH],621:[DW_LIGHT]}

def render_shop() :
    html = "<html><head><body><font color=\"LEVEL\">Robe Armor Fabrics:</font><table border=0 width=300>"
    for i in SHOP_LIST.keys() :
       html += "<tr><td width=35 height=45><img src=icon."+SHOP_LIST[i][0]+" width=32 height=32 align=left></td><td width=365 valign=top><table border=0 width=100%>"
       html += "<tr><td><a action=\"bypass -h Quest 374_WhisperOfDreams1 "+str(i)+"\"><font color=\"FFFFFF\">"+SHOP_LIST[i][3]+" x"+str(SHOP_LIST[i][1])+"</font></a></td></tr>"
       html += "<tr><td><a action=\"bypass -h Quest 374_WhisperOfDreams1 "+str(i)+"\"><font color=\"B09878\">"+str(SHOP_LIST[i][2]*ADENA_X)+" adena</font></a></td></tr></table></td></tr>"
    html += "</table></body></html>"
    return html

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    id = st.getState() 
    htmltext = event
    if event == "7515-4.htm" :
       st.setState(STARTING)
       st.set("cond","1")
       st.playSound("ItemSound.quest_accept")
    elif event == "7515-5.htm" :
       st.takeItems(CB_TOOTH,-1)
       st.takeItems(DW_LIGHT,-1)
       st.takeItems(SEALD_MSTONE,-1)
       st.exitQuest(1)
    elif event == "7515-6.htm" :
       if st.getQuestItemsCount(CB_TOOTH)==st.getQuestItemsCount(DW_LIGHT)==65 :
          st.set("allow","1")
          st.takeItems(CB_TOOTH,-1)
          st.takeItems(DW_LIGHT,-1)
          htmltext = "7515-7.htm"
    elif event == "7515-8.htm" :
       if st.getQuestItemsCount(SEALD_MSTONE) :
          if id == STARTING :
             st.setState(STARTED)
             st.set("cond","2")
             htmltext = "7515-9.htm"
          elif id == STARTED :
             htmltext = "7515-10.htm"
    elif event == "buy" :
       htmltext = render_shop()
    elif int(event) in SHOP_LIST.keys() :
       price = SHOP_LIST[int(event)][2]*ADENA_X
       if st.getQuestItemsCount(57) >= price :
          st.takeItems(57,price)
          st.giveItems(int(event),SHOP_LIST[int(event)][1])
          st.playSound("ItemSound.quest_finish")
          st.exitQuest(1)
          htmltext = "7515-11.htm"
       else :
          htmltext = "Not enough adena"
    return htmltext

 def onTalk (self,npc,st):
   htmltext = default
   id = st.getState()
   npcid = npc.getNpcId()
   if npcid == MANAKIA:
      if id == CREATED :
         st.set("cond","0")
         st.set("allow","0")
         htmltext = "7515-1.htm"
         if st.getPlayer().getLevel() < 56 :
            st.exitQuest(1)
            htmltext = "7515-2.htm"
      else :
         if int(st.get("allow")) :
            htmltext = "7515-3.htm"
         else :
            htmltext = "7515-3a.htm"
   elif npcid == TORAI :
      if st.getQuestItemsCount(SEALD_MSTONE) :
         htmltext = "7557-1.htm"
         st.takeItems(SEALD_MSTONE,1)
         st.giveItems(MSTONE,1)
         st.set("cond","3")
         st.playSound("ItemSound.quest_middle")
   return htmltext

 def onKill (self,npc,st) :
     npcid = npc.getNpcId()
     item  = DROPLIST[npcid][0]
     count = st.getQuestItemsCount(item)
     if count < 65 and st.getRandom(DROP_MAX) < DROP_RATE :
        st.giveItems(item,1)
        if count + 1 >= 65 :
           st.playSound("ItemSound.quest_middle")
        else :
           st.playSound("ItemSound.quest_itemget")
     if st.getState() == STARTING :   
        if st.getRandom(DROP_MAX_2) < DROP_RATE_2  and not st.getQuestItemsCount(SEALD_MSTONE) :
           st.giveItems(SEALD_MSTONE,1)
           st.playSound("ItemSound.quest_middle")
     return  

# Quest class and state definition
QUEST       = Quest(QUEST_NUMBER, str(QUEST_NUMBER)+"_"+QUEST_NAME, QUEST_DESCRIPTION)

CREATED     = State('Start',     QUEST)
STARTING    = State('Starting',  QUEST)
STARTED     = State('Started',   QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

# Quest NPC starter initialization
QUEST.addStartNpc(MANAKIA)
# Quest initialization
CREATED.addTalkId(MANAKIA)
STARTING.addTalkId(MANAKIA)
STARTED.addTalkId(MANAKIA)
STARTED.addTalkId(TORAI)

for i in DROPLIST.keys() :
  STARTING.addKillId(i)
  STARTED.addKillId(i)
  STARTING.addQuestDrop(i,DROPLIST[i][0],1)

print str(QUEST_NUMBER)+": "+QUEST_DESCRIPTION
