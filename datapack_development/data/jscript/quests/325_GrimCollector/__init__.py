# Made by Mr. Have fun! - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ZOMBIE_HEAD1_ID = 1350
ZOMBIE_HEART1_ID = 1351
ZOMBIE_LIVER1_ID = 1352
SKULL1_ID = 1353
RIB_BONE1_ID = 1354
SPINE1_ID = 1355
ARM_BONE1_ID = 1356
THIGH_BONE1_ID = 1357
COMPLETE_SKELETON_ID = 1358
ANATOMY_DIAGRAM_ID = 1349
ADENA_ID = 57

def pieces(st):
    return st.getQuestItemsCount(ZOMBIE_HEAD1_ID)+\
           st.getQuestItemsCount(SPINE1_ID)+\
           st.getQuestItemsCount(ARM_BONE1_ID)+\
           st.getQuestItemsCount(ZOMBIE_HEART1_ID)+\
           st.getQuestItemsCount(ZOMBIE_LIVER1_ID)+\
           st.getQuestItemsCount(SKULL1_ID)+\
           st.getQuestItemsCount(RIB_BONE1_ID)+\
           st.getQuestItemsCount(THIGH_BONE1_ID)+\
           st.getQuestItemsCount(COMPLETE_SKELETON_ID)

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7336-03.htm" :
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "7434-03.htm" :
      st.giveItems(ANATOMY_DIAGRAM_ID,1)
    elif event == "7434-06.htm" :
      if pieces(st) > 0 :  
         st.giveItems(ADENA_ID,30*st.getQuestItemsCount(ZOMBIE_HEAD1_ID)+20*st.getQuestItemsCount(ZOMBIE_HEART1_ID)+20*st.getQuestItemsCount(ZOMBIE_LIVER1_ID)+50*st.getQuestItemsCount(SKULL1_ID)+15*st.getQuestItemsCount(RIB_BONE1_ID)+10*st.getQuestItemsCount(SPINE1_ID)+10*st.getQuestItemsCount(ARM_BONE1_ID)+10*st.getQuestItemsCount(THIGH_BONE1_ID)+2000*st.getQuestItemsCount(COMPLETE_SKELETON_ID))
         st.takeItems(ZOMBIE_HEAD1_ID,-1)
         st.takeItems(ZOMBIE_HEART1_ID,-1)
         st.takeItems(ZOMBIE_LIVER1_ID,-1)
         st.takeItems(SKULL1_ID,-1)
         st.takeItems(RIB_BONE1_ID,-1)
         st.takeItems(SPINE1_ID,-1)
         st.takeItems(ARM_BONE1_ID,-1)
         st.takeItems(THIGH_BONE1_ID,-1)
         st.takeItems(COMPLETE_SKELETON_ID,-1)
      st.takeItems(ANATOMY_DIAGRAM_ID,-1)
      st.playSound("ItemSound.quest_finish")
      st.exitQuest(1)
    elif event == "7434-07.htm" :
      if pieces(st) > 0 :  
         st.giveItems(ADENA_ID,30*st.getQuestItemsCount(ZOMBIE_HEAD1_ID)+20*st.getQuestItemsCount(ZOMBIE_HEART1_ID)+20*st.getQuestItemsCount(ZOMBIE_LIVER1_ID)+50*st.getQuestItemsCount(SKULL1_ID)+15*st.getQuestItemsCount(RIB_BONE1_ID)+10*st.getQuestItemsCount(SPINE1_ID)+10*st.getQuestItemsCount(ARM_BONE1_ID)+10*st.getQuestItemsCount(THIGH_BONE1_ID)+2000*st.getQuestItemsCount(COMPLETE_SKELETON_ID))
         st.takeItems(ZOMBIE_HEAD1_ID,-1)
         st.takeItems(ZOMBIE_HEART1_ID,-1)
         st.takeItems(ZOMBIE_LIVER1_ID,-1)
         st.takeItems(SKULL1_ID,-1)
         st.takeItems(RIB_BONE1_ID,-1)
         st.takeItems(SPINE1_ID,-1)
         st.takeItems(ARM_BONE1_ID,-1)
         st.takeItems(THIGH_BONE1_ID,-1)
         st.takeItems(COMPLETE_SKELETON_ID,-1)
    elif event == "7434-09.htm" :
      st.giveItems(ADENA_ID,2000*st.getQuestItemsCount(COMPLETE_SKELETON_ID))
      st.takeItems(COMPLETE_SKELETON_ID,-1)
    elif event == "7342-03.htm" :
      if st.getQuestItemsCount(SPINE1_ID) and st.getQuestItemsCount(ARM_BONE1_ID) and st.getQuestItemsCount(SKULL1_ID) and st.getQuestItemsCount(RIB_BONE1_ID) and st.getQuestItemsCount(THIGH_BONE1_ID) :
         st.takeItems(SPINE1_ID,1)
         st.takeItems(SKULL1_ID,1)
         st.takeItems(ARM_BONE1_ID,1)
         st.takeItems(RIB_BONE1_ID,1)
         st.takeItems(THIGH_BONE1_ID,1) 
         if st.getRandom(5)<4 :
            st.giveItems(COMPLETE_SKELETON_ID,1)
         else:
            htmltext = "7342-04.htm"
      else:
         htmltext = "7342-02.htm"
    return htmltext


 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if npcId == 7336 and int(st.get("cond"))==0 :
      if st.getPlayer().getLevel() >= 15 :
         htmltext = "7336-02.htm"
         return htmltext
      else:
         htmltext = "7336-01.htm"
         st.exitQuest(1)
   elif npcId == 7336 and int(st.get("cond")) and st.getQuestItemsCount(ANATOMY_DIAGRAM_ID) == 0 :
      htmltext = "7336-04.htm"
   elif npcId == 7336 and int(st.get("cond")) and st.getQuestItemsCount(ANATOMY_DIAGRAM_ID) :
      htmltext = "7336-05.htm"
   elif npcId == 7434 and int(st.get("cond")) and st.getQuestItemsCount(ANATOMY_DIAGRAM_ID) == 0 :
      htmltext = "7434-01.htm"
   elif npcId == 7434 and int(st.get("cond")) and st.getQuestItemsCount(ANATOMY_DIAGRAM_ID) and not pieces(st) :
      htmltext = "7434-04.htm"
   elif npcId == 7434 and int(st.get("cond")) and st.getQuestItemsCount(ANATOMY_DIAGRAM_ID) and pieces(st) and not st.getQuestItemsCount(COMPLETE_SKELETON_ID):
      htmltext = "7434-05.htm"
   elif npcId == 7434 and int(st.get("cond")) and st.getQuestItemsCount(ANATOMY_DIAGRAM_ID) and pieces(st) and st.getQuestItemsCount(COMPLETE_SKELETON_ID) :
      htmltext = "7434-08.htm"
   elif npcId == 7342 and int(st.get("cond")) and st.getQuestItemsCount(ANATOMY_DIAGRAM_ID) :
      htmltext = "7342-01.htm"
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   if st.getQuestItemsCount(ANATOMY_DIAGRAM_ID) :
    n = st.getRandom(100)
    if npcId == 26 :
     if n<90 :
      st.playSound("ItemSound.quest_itemget")   
      if n<40 :
         st.giveItems(ZOMBIE_HEAD1_ID,1)
      elif n<60 :
         st.giveItems(ZOMBIE_HEART1_ID,1)
      else :
         st.giveItems(ZOMBIE_LIVER1_ID,1)
    elif npcId == 29 :
      st.playSound("ItemSound.quest_itemget")  
      if n<44 :
         st.giveItems(ZOMBIE_HEAD1_ID,1)
      elif n<66 :
         st.giveItems(ZOMBIE_HEART1_ID,1)
      else :
        st.giveItems(ZOMBIE_LIVER1_ID,1)
    elif npcId == 35 :
     if n<79 :
      st.playSound("ItemSound.quest_itemget")
      if n<5 :
        st.giveItems(SKULL1_ID,1)
      elif n<15 :
         st.giveItems(RIB_BONE1_ID,1)
      elif n<29 :
         st.giveItems(SPINE1_ID,1)
      else :
         st.giveItems(THIGH_BONE1_ID,1)
    elif npcId == 42 :
     if n<86 :
      st.playSound("ItemSound.quest_itemget")   
      if n<6 :
         st.giveItems(SKULL1_ID,1)
      elif n<19 :
         st.giveItems(RIB_BONE1_ID,1)
      elif n<69 :
         st.giveItems(ARM_BONE1_ID,1)
      else :
         st.giveItems(THIGH_BONE1_ID,1)
    elif npcId == 45 :
     if n<97 :
      st.playSound("ItemSound.quest_itemget")
      if n<9 :
         st.giveItems(SKULL1_ID,1)
      elif n<59 :
         st.giveItems(SPINE1_ID,1)
      elif n<77 :
         st.giveItems(ARM_BONE1_ID,1)
      else :
         st.giveItems(THIGH_BONE1_ID,1)
    elif npcId == 51 :
     if n<99 :
      st.playSound("ItemSound.quest_itemget")
      if n<9 :
         st.giveItems(SKULL1_ID,1)
      elif n<59 :
         st.giveItems(RIB_BONE1_ID,1)
      elif n<79 :
         st.giveItems(SPINE1_ID,1)
      else :
         st.giveItems(ARM_BONE1_ID,1)
    elif npcId == 514 :
     if n<51 :
      st.playSound("ItemSound.quest_itemget")
      if n<2 :
         st.giveItems(SKULL1_ID,1)
      elif n<8 :
         st.giveItems(RIB_BONE1_ID,1)
      elif n<17 :
         st.giveItems(SPINE1_ID,1)
      elif n<18 :
         st.giveItems(ARM_BONE1_ID,1)
      else :
         st.giveItems(THIGH_BONE1_ID,1)
    elif npcId == 515 :
     if n<60 :
      st.playSound("ItemSound.quest_itemget")   
      if n<3 :
         st.giveItems(SKULL1_ID,1)
      elif n<11 :
         st.giveItems(RIB_BONE1_ID,1)
      elif n<22 :
         st.giveItems(SPINE1_ID,1)
      elif n<24 :
         st.giveItems(ARM_BONE1_ID,1)
      else :
         st.giveItems(THIGH_BONE1_ID,1)
    elif npcId == 457 :
      st.playSound("ItemSound.quest_itemget")
      if n<42 :
         st.giveItems(ZOMBIE_HEAD1_ID,1)
      elif n<67 :
         st.giveItems(ZOMBIE_HEART1_ID,1)
      else :
         st.giveItems(ZOMBIE_LIVER1_ID,1)
    elif npcId == 458 :
      st.playSound("ItemSound.quest_itemget")  
      if n<42 :
         st.giveItems(ZOMBIE_HEAD1_ID,1)
      elif n<67 :
         st.giveItems(ZOMBIE_HEART1_ID,1)
      else :
         st.giveItems(ZOMBIE_LIVER1_ID,1)
   return

QUEST       = Quest(325,"325_GrimCollector","Grim Collector")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7336)
CREATED.addTalkId(7336)

STARTED.addTalkId(7336)
STARTED.addTalkId(7342)
STARTED.addTalkId(7434)

for i in [26,29,35,42,45,457,458,51,514,515] :
    STARTED.addKillId(i)

STARTED.addQuestDrop(26,ZOMBIE_HEAD1_ID,1)
STARTED.addQuestDrop(26,ZOMBIE_HEART1_ID,1)
STARTED.addQuestDrop(26,ZOMBIE_LIVER1_ID,1)
STARTED.addQuestDrop(35,SKULL1_ID,1)
STARTED.addQuestDrop(42,RIB_BONE1_ID,1)
STARTED.addQuestDrop(45,SPINE1_ID,1)
STARTED.addQuestDrop(51,ARM_BONE1_ID,1)
STARTED.addQuestDrop(514,THIGH_BONE1_ID,1)
STARTED.addQuestDrop(7342,COMPLETE_SKELETON_ID,1)
STARTED.addQuestDrop(7434,ANATOMY_DIAGRAM_ID,1)

print "importing quests: 325: Grim Collector"
