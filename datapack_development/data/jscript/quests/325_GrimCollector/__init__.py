# Maked by Mr. Have fun! Version 0.2
print "importing quests: 325: Grim Collector"
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

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "7336-03.htm"
    elif event == "7434_1" :
          htmltext = "7434-02.htm"
    elif event == "7434_2" :
          htmltext = "7434-03.htm"
          st.giveItems(ANATOMY_DIAGRAM_ID,1)
    elif event == "7434_3" :
          if st.getGameTicks() != int(st.get("id")) :
            st.set("id",str(st.getGameTicks()))
            htmltext = "7434-06.htm"
            st.giveItems(ADENA_ID,30*st.getQuestItemsCount(ZOMBIE_HEAD1_ID)+20*st.getQuestItemsCount(ZOMBIE_HEART1_ID)+20*st.getQuestItemsCount(ZOMBIE_LIVER1_ID)+50*st.getQuestItemsCount(SKULL1_ID)+15*st.getQuestItemsCount(RIB_BONE1_ID)+10*st.getQuestItemsCount(SPINE1_ID)+10*st.getQuestItemsCount(ARM_BONE1_ID)+10*st.getQuestItemsCount(THIGH_BONE1_ID)+2000*st.getQuestItemsCount(COMPLETE_SKELETON_ID))
            st.takeItems(ZOMBIE_HEAD1_ID,st.getQuestItemsCount(ZOMBIE_HEAD1_ID))
            st.takeItems(ZOMBIE_HEART1_ID,st.getQuestItemsCount(ZOMBIE_HEART1_ID))
            st.takeItems(ZOMBIE_LIVER1_ID,st.getQuestItemsCount(ZOMBIE_LIVER1_ID))
            st.takeItems(SKULL1_ID,st.getQuestItemsCount(SKULL1_ID))
            st.takeItems(RIB_BONE1_ID,st.getQuestItemsCount(RIB_BONE1_ID))
            st.takeItems(SPINE1_ID,st.getQuestItemsCount(SPINE1_ID))
            st.takeItems(ARM_BONE1_ID,st.getQuestItemsCount(ARM_BONE1_ID))
            st.takeItems(THIGH_BONE1_ID,st.getQuestItemsCount(THIGH_BONE1_ID))
            st.takeItems(COMPLETE_SKELETON_ID,st.getQuestItemsCount(COMPLETE_SKELETON_ID))
            st.takeItems(ANATOMY_DIAGRAM_ID,1)
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
    elif event == "7434_4" :
          if st.getGameTicks() != int(st.get("id")) :
            st.set("id",str(st.getGameTicks()))
            htmltext = "7434-07.htm"
            st.giveItems(ADENA_ID,30*st.getQuestItemsCount(ZOMBIE_HEAD1_ID)+20*st.getQuestItemsCount(ZOMBIE_HEART1_ID)+20*st.getQuestItemsCount(ZOMBIE_LIVER1_ID)+50*st.getQuestItemsCount(SKULL1_ID)+15*st.getQuestItemsCount(RIB_BONE1_ID)+10*st.getQuestItemsCount(SPINE1_ID)+10*st.getQuestItemsCount(ARM_BONE1_ID)+10*st.getQuestItemsCount(THIGH_BONE1_ID)+2000*st.getQuestItemsCount(COMPLETE_SKELETON_ID))
            st.takeItems(ZOMBIE_HEAD1_ID,st.getQuestItemsCount(ZOMBIE_HEAD1_ID))
            st.takeItems(ZOMBIE_HEART1_ID,st.getQuestItemsCount(ZOMBIE_HEART1_ID))
            st.takeItems(ZOMBIE_LIVER1_ID,st.getQuestItemsCount(ZOMBIE_LIVER1_ID))
            st.takeItems(SKULL1_ID,st.getQuestItemsCount(SKULL1_ID))
            st.takeItems(RIB_BONE1_ID,st.getQuestItemsCount(RIB_BONE1_ID))
            st.takeItems(SPINE1_ID,st.getQuestItemsCount(SPINE1_ID))
            st.takeItems(ARM_BONE1_ID,st.getQuestItemsCount(ARM_BONE1_ID))
            st.takeItems(THIGH_BONE1_ID,st.getQuestItemsCount(THIGH_BONE1_ID))
            st.takeItems(COMPLETE_SKELETON_ID,st.getQuestItemsCount(COMPLETE_SKELETON_ID))
    elif event == "7434_5" :
          if st.getGameTicks() != int(st.get("id")) :
            st.set("id",str(st.getGameTicks()))
            htmltext = "7434-09.htm"
            st.giveItems(ADENA_ID,2000*st.getQuestItemsCount(COMPLETE_SKELETON_ID))
            st.takeItems(COMPLETE_SKELETON_ID,st.getQuestItemsCount(COMPLETE_SKELETON_ID))
    elif event == "7342_1" :
          if st.getQuestItemsCount(SPINE1_ID) and st.getQuestItemsCount(ARM_BONE1_ID) and st.getQuestItemsCount(SKULL1_ID) and st.getQuestItemsCount(RIB_BONE1_ID) and st.getQuestItemsCount(THIGH_BONE1_ID) :
            if st.getRandom(5)<4 :
              htmltext = "7342-03.htm"
              st.takeItems(SPINE1_ID,1)
              st.takeItems(SKULL1_ID,1)
              st.takeItems(ARM_BONE1_ID,1)
              st.takeItems(RIB_BONE1_ID,1)
              st.takeItems(THIGH_BONE1_ID,1)
              st.giveItems(COMPLETE_SKELETON_ID,1)
            else:
              st.takeItems(SPINE1_ID,1)
              st.takeItems(SKULL1_ID,1)
              st.takeItems(ARM_BONE1_ID,1)
              st.takeItems(RIB_BONE1_ID,1)
              st.takeItems(THIGH_BONE1_ID,1)
              htmltext = "7342-04.htm"
          else:
            htmltext = "7342-02.htm"
    return htmltext


 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7336 and int(st.get("cond"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getLevel() >= 15 :
          htmltext = "7336-02.htm"
          return htmltext
        else:
          htmltext = "7336-01.htm"
          st.exitQuest(1)
      else:
        htmltext = "7336-01.htm"
        st.exitQuest(1)
   elif npcId == 7336 and int(st.get("cond"))==1 and st.getQuestItemsCount(ANATOMY_DIAGRAM_ID)==0 :
      htmltext = "7336-04.htm"
   elif npcId == 7336 and int(st.get("cond"))==1 and st.getQuestItemsCount(ANATOMY_DIAGRAM_ID)==1 :
      htmltext = "7336-05.htm"
   elif npcId == 7434 and (int(st.get("cond"))==1) and st.getQuestItemsCount(ANATOMY_DIAGRAM_ID)==0 :
      htmltext = "7434-01.htm"
   elif npcId == 7434 and (int(st.get("cond"))==1) and st.getQuestItemsCount(ANATOMY_DIAGRAM_ID)==1 and (st.getQuestItemsCount(ZOMBIE_HEAD1_ID)+st.getQuestItemsCount(SPINE1_ID)+st.getQuestItemsCount(ARM_BONE1_ID)+st.getQuestItemsCount(ZOMBIE_HEART1_ID)+st.getQuestItemsCount(ZOMBIE_LIVER1_ID)+st.getQuestItemsCount(SKULL1_ID)+st.getQuestItemsCount(RIB_BONE1_ID)+st.getQuestItemsCount(THIGH_BONE1_ID)+st.getQuestItemsCount(COMPLETE_SKELETON_ID)<1) :
      htmltext = "7434-04.htm"
   elif npcId == 7434 and (int(st.get("cond"))==1) and st.getQuestItemsCount(COMPLETE_SKELETON_ID)==0 and st.getQuestItemsCount(ANATOMY_DIAGRAM_ID)==1 and (st.getQuestItemsCount(ZOMBIE_HEAD1_ID)+st.getQuestItemsCount(SPINE1_ID)+st.getQuestItemsCount(ARM_BONE1_ID)+st.getQuestItemsCount(ZOMBIE_HEART1_ID)+st.getQuestItemsCount(ZOMBIE_LIVER1_ID)+st.getQuestItemsCount(SKULL1_ID)+st.getQuestItemsCount(RIB_BONE1_ID)+st.getQuestItemsCount(THIGH_BONE1_ID)>0) :
      htmltext = "7434-05.htm"
   elif npcId == 7434 and (int(st.get("cond"))==1) and st.getQuestItemsCount(COMPLETE_SKELETON_ID)>0 and st.getQuestItemsCount(ANATOMY_DIAGRAM_ID)==1 and (st.getQuestItemsCount(ZOMBIE_HEAD1_ID)+st.getQuestItemsCount(SPINE1_ID)+st.getQuestItemsCount(ARM_BONE1_ID)+st.getQuestItemsCount(ZOMBIE_HEART1_ID)+st.getQuestItemsCount(ZOMBIE_LIVER1_ID)+st.getQuestItemsCount(SKULL1_ID)+st.getQuestItemsCount(RIB_BONE1_ID)+st.getQuestItemsCount(THIGH_BONE1_ID)>0) :
      htmltext = "7434-08.htm"
   elif npcId == 7342 and (int(st.get("cond"))==1) and st.getQuestItemsCount(ANATOMY_DIAGRAM_ID)==1 :
      htmltext = "7342-01.htm"
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 26 :
        st.set("id","0")
        if st.getQuestItemsCount(ANATOMY_DIAGRAM_ID) and int(st.get("cond")) :
          n = st.getRandom(10)
          if n<4 :
            st.giveItems(ZOMBIE_HEAD1_ID,1)
            st.playSound("ItemSound.quest_itemget")
          elif n<6 :
              st.giveItems(ZOMBIE_HEART1_ID,1)
              st.playSound("ItemSound.quest_itemget")
          elif n<9 :
              st.giveItems(ZOMBIE_LIVER1_ID,1)
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 29 :
        st.set("id","0")
        if st.getQuestItemsCount(ANATOMY_DIAGRAM_ID) and int(st.get("cond")) :
          n = st.getRandom(100)
          if n<44 :
            st.giveItems(ZOMBIE_HEAD1_ID,1)
            st.playSound("ItemSound.quest_itemget")
          elif n<66 :
            st.giveItems(ZOMBIE_HEART1_ID,1)
            st.playSound("ItemSound.quest_itemget")
          else:
            st.giveItems(ZOMBIE_LIVER1_ID,1)
            st.playSound("ItemSound.quest_itemget")
   elif npcId == 35 :
        st.set("id","0")
        if st.getQuestItemsCount(ANATOMY_DIAGRAM_ID) and int(st.get("cond")) :
          n = st.getRandom(100)
          if n<5 :
            st.giveItems(SKULL1_ID,1)
            st.playSound("ItemSound.quest_itemget")
          elif n<15 :
              st.giveItems(RIB_BONE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
          elif n<29 :
              st.giveItems(SPINE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
          elif n<79 :
              st.giveItems(THIGH_BONE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 42 :
        st.set("id","0")
        if st.getQuestItemsCount(ANATOMY_DIAGRAM_ID) and int(st.get("cond")) :
          n = st.getRandom(100)
          if n<6 :
            st.giveItems(SKULL1_ID,1)
            st.playSound("ItemSound.quest_itemget")
          elif n<19 :
              st.giveItems(RIB_BONE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
          elif n<69 :
              st.giveItems(ARM_BONE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
          elif n<86 :
              st.giveItems(THIGH_BONE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 45 :
        st.set("id","0")
        if st.getQuestItemsCount(ANATOMY_DIAGRAM_ID) and int(st.get("cond")) :
          n = st.getRandom(100)
          if n<9 :
            st.giveItems(SKULL1_ID,1)
            st.playSound("ItemSound.quest_itemget")
          elif n<59 :
              st.giveItems(SPINE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
          elif n<77 :
              st.giveItems(ARM_BONE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
          elif n<97 :
              st.giveItems(THIGH_BONE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 51 :
        st.set("id","0")
        if st.getQuestItemsCount(ANATOMY_DIAGRAM_ID) and int(st.get("cond")) :
          n = st.getRandom(100)
          if n<9 :
            st.giveItems(SKULL1_ID,1)
            st.playSound("ItemSound.quest_itemget")
          elif n<59 :
              st.giveItems(RIB_BONE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
          elif n<79 :
              st.giveItems(SPINE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
          elif n<99 :
              st.giveItems(ARM_BONE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 514 :
        st.set("id","0")
        if st.getQuestItemsCount(ANATOMY_DIAGRAM_ID) and int(st.get("cond")) :
          n = st.getRandom(100)
          if n<2 :
            st.giveItems(SKULL1_ID,1)
            st.playSound("ItemSound.quest_itemget")
          elif n<8 :
              st.giveItems(RIB_BONE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
          elif n<17 :
              st.giveItems(SPINE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
          elif n<18 :
              st.giveItems(ARM_BONE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
          elif n<51 :
              st.giveItems(THIGH_BONE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 515 :
        st.set("id","0")
        if st.getQuestItemsCount(ANATOMY_DIAGRAM_ID) and int(st.get("cond")) :
          n = st.getRandom(100)
          if n<3 :
            st.giveItems(SKULL1_ID,1)
            st.playSound("ItemSound.quest_itemget")
          elif n<11 :
              st.giveItems(RIB_BONE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
          elif n<22 :
              st.giveItems(SPINE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
          elif n<24 :
              st.giveItems(ARM_BONE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
          elif n<60 :
              st.giveItems(THIGH_BONE1_ID,1)
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 457 :
        st.set("id","0")
        if st.getQuestItemsCount(ANATOMY_DIAGRAM_ID) and int(st.get("cond")) :
          n = st.getRandom(100)
          if n<42 :
            st.giveItems(ZOMBIE_HEAD1_ID,1)
            st.playSound("ItemSound.quest_itemget")
          elif n<67 :
              st.giveItems(ZOMBIE_HEART1_ID,1)
              st.playSound("ItemSound.quest_itemget")
          elif n<100 :
              st.giveItems(ZOMBIE_LIVER1_ID,1)
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 458 :
        st.set("id","0")
        if st.getQuestItemsCount(ANATOMY_DIAGRAM_ID) and int(st.get("cond")) :
          n = st.getRandom(100)
          if n<42 :
            st.giveItems(ZOMBIE_HEAD1_ID,1)
            st.playSound("ItemSound.quest_itemget")
          elif n<67 :
              st.giveItems(ZOMBIE_HEART1_ID,1)
              st.playSound("ItemSound.quest_itemget")
          elif n<100 :
              st.giveItems(ZOMBIE_LIVER1_ID,1)
              st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(325,"325_GrimCollector","Grim Collector")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7336)

STARTING.addTalkId(7336)

STARTED.addTalkId(7336)
STARTED.addTalkId(7342)
STARTED.addTalkId(7434)

STARTED.addKillId(26)
STARTED.addKillId(29)
STARTED.addKillId(35)
STARTED.addKillId(42)
STARTED.addKillId(45)
STARTED.addKillId(457)
STARTED.addKillId(458)
STARTED.addKillId(51)
STARTED.addKillId(514)
STARTED.addKillId(515)

STARTED.addQuestDrop(26,ZOMBIE_HEAD1_ID,1)
STARTED.addQuestDrop(29,ZOMBIE_HEAD1_ID,1)
STARTED.addQuestDrop(457,ZOMBIE_HEAD1_ID,1)
STARTED.addQuestDrop(458,ZOMBIE_HEAD1_ID,1)
STARTED.addQuestDrop(26,ZOMBIE_HEART1_ID,1)
STARTED.addQuestDrop(29,ZOMBIE_HEART1_ID,1)
STARTED.addQuestDrop(457,ZOMBIE_HEART1_ID,1)
STARTED.addQuestDrop(458,ZOMBIE_HEART1_ID,1)
STARTED.addQuestDrop(26,ZOMBIE_LIVER1_ID,1)
STARTED.addQuestDrop(29,ZOMBIE_LIVER1_ID,1)
STARTED.addQuestDrop(457,ZOMBIE_LIVER1_ID,1)
STARTED.addQuestDrop(458,ZOMBIE_LIVER1_ID,1)
STARTED.addQuestDrop(35,SKULL1_ID,1)
STARTED.addQuestDrop(42,SKULL1_ID,1)
STARTED.addQuestDrop(45,SKULL1_ID,1)
STARTED.addQuestDrop(51,SKULL1_ID,1)
STARTED.addQuestDrop(514,SKULL1_ID,1)
STARTED.addQuestDrop(515,SKULL1_ID,1)
STARTED.addQuestDrop(35,RIB_BONE1_ID,1)
STARTED.addQuestDrop(42,RIB_BONE1_ID,1)
STARTED.addQuestDrop(51,RIB_BONE1_ID,1)
STARTED.addQuestDrop(514,RIB_BONE1_ID,1)
STARTED.addQuestDrop(515,RIB_BONE1_ID,1)
STARTED.addQuestDrop(35,SPINE1_ID,1)
STARTED.addQuestDrop(45,SPINE1_ID,1)
STARTED.addQuestDrop(51,SPINE1_ID,1)
STARTED.addQuestDrop(514,SPINE1_ID,1)
STARTED.addQuestDrop(515,SPINE1_ID,1)
STARTED.addQuestDrop(42,ARM_BONE1_ID,1)
STARTED.addQuestDrop(45,ARM_BONE1_ID,1)
STARTED.addQuestDrop(51,ARM_BONE1_ID,1)
STARTED.addQuestDrop(514,ARM_BONE1_ID,1)
STARTED.addQuestDrop(515,ARM_BONE1_ID,1)
STARTED.addQuestDrop(35,THIGH_BONE1_ID,1)
STARTED.addQuestDrop(42,THIGH_BONE1_ID,1)
STARTED.addQuestDrop(45,THIGH_BONE1_ID,1)
STARTED.addQuestDrop(514,THIGH_BONE1_ID,1)
STARTED.addQuestDrop(515,THIGH_BONE1_ID,1)
STARTED.addQuestDrop(7342,COMPLETE_SKELETON_ID,1)
STARTED.addQuestDrop(7434,ANATOMY_DIAGRAM_ID,1)
