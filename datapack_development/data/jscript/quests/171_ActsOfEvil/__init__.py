# Made by mtrix
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

BLADE_MOLD,TYRAS_BILL,RANGERS_REPORT1,RANGERS_REPORT2,RANGERS_REPORT3,RANGERS_REPORT4,\
WEAPON_TRADE_CONTRACT,ATTACK_DIRECTIVES,CERTIFICATE,CARGOBOX,OL_MAHUM_HEAD = range(4239,4250)

ADENA = 57

CHANCE = 400000
CHANCE2 = 300000
CHANCE21 = 300000
CHANCE22 = 300000
CHANCE23 = 300000
CHANCE24 = 200000
CHANCE25 = 200000
CHANCE3 = 150000

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
     htmltext = event
     if event == "30381-02.htm" :
         st.setState(STARTED)
         st.set("cond","1")
         st.playSound("ItemSound.quest_accept")
     elif event == "30207-02.htm" :
         st.set("cond","2")
     elif event == "30381-04.htm" :
         st.set("cond","5")
     elif event == "30381-07.htm" :
         st.set("cond","7")
         st.takeItems(WEAPON_TRADE_CONTRACT,-1)
         st.playSound("ItemSound.quest_middle")
     elif event == "30437-03.htm" :
         st.giveItems(CARGOBOX,1)
         st.giveItems(CERTIFICATE,1)
         st.set("cond","9")
     elif event == "30617-04.htm" :
         st.takeItems(CERTIFICATE,-1)
         st.takeItems(ATTACK_DIRECTIVES,-1)
         st.takeItems(CARGOBOX,-1)
         st.set("cond","10")
     return htmltext

 def onTalk (Self,npc,st):
     npcId = npc.getNpcId()
     htmltext = "<html><head><body>I have nothing to say you</body></html>"
     id = st.getState()
     level = st.getPlayer().getLevel()
     cond = st.getInt("cond")
     if npcId==30381 :
         if id == CREATED :
            if level > 26:
               htmltext = "30381-01.htm"
            else :
               htmltext = "30381-01a.htm"
               st.exitQuest(1)
         elif id == COMPLETED :
             htmltext = "<html><head><body>This quest has already been completed!</body></html>"
         elif cond==1 :
             htmltext = "30381-02a.htm"
         elif cond==4 :
             htmltext = "30381-03.htm"
         elif cond==5 :
             if st.getQuestItemsCount(RANGERS_REPORT1) and st.getQuestItemsCount(RANGERS_REPORT2) and st.getQuestItemsCount(RANGERS_REPORT3) and st.getQuestItemsCount(RANGERS_REPORT4) :
                 htmltext = "30381-05.htm"
                 st.takeItems(RANGERS_REPORT1,-1)
                 st.takeItems(RANGERS_REPORT2,-1)
                 st.takeItems(RANGERS_REPORT3,-1)
                 st.takeItems(RANGERS_REPORT4,-1)
                 st.set("cond","6")
             else :
                 htmltext = "30381-04a.htm"
         elif cond==6 :
             if st.getQuestItemsCount(WEAPON_TRADE_CONTRACT) and st.getQuestItemsCount(ATTACK_DIRECTIVES) :
                 htmltext = "30381-06.htm"
             else :
                 htmltext = "30381-05a.htm"
         elif cond==7 :
             htmltext = "30381-07a.htm"
         elif cond==11 :
             htmltext = "30381-08.htm"
             st.giveItems(ADENA,90000)
             st.playSound("ItemSound.quest_finish")
             st.setState(COMPLETED)
     elif npcId==30207 :
         if cond==1 :
             htmltext = "30207-01.htm"
         elif cond==2 :
             htmltext = "30207-01a.htm"
         elif cond==3 :
             if st.getQuestItemsCount(TYRAS_BILL) :
                 st.takeItems(TYRAS_BILL,-1)
                 htmltext = "30207-03.htm"
                 st.set("cond","4")
             else :
                 htmltext = "30207-01a.htm"
         elif cond==4 :
             htmltext = "30207-03a.htm"
     elif npcId==30420 :
         if cond==2 and st.getQuestItemsCount(BLADE_MOLD)>=20 :
            st.takeItems(BLADE_MOLD,-1)
            st.giveItems(TYRAS_BILL,1)
            htmltext = "30420-01.htm"
            st.set("cond","3")
            #mmh.. we're lacking a some text here in case you dont have 20 molds?
         if cond==3 :
             htmltext = "30420-01a.htm"
     elif npcId==30425 :
         if cond==7 :
             htmltext = "30425-01.htm"
             st.set("cond","8")
         elif cond==8 :
             htmltext = "30425-02.htm"
     elif npcId==30437 :
         if cond==8 :
             htmltext = "30437-01.htm"
         elif cond==9 :
             htmltext = "30437-03a.htm"
     elif npcId==30617 :
         if cond==9 and st.getQuestItemsCount(CERTIFICATE) and st.getQuestItemsCount(CARGOBOX) and st.getQuestItemsCount(ATTACK_DIRECTIVES) :
             htmltext = "30617-01.htm"
         if cond==10 :
             if st.getQuestItemsCount(OL_MAHUM_HEAD)>=30 :
                 htmltext = "30617-05.htm"
                 st.giveItems(ADENA,8000)
                 st.takeItems(OL_MAHUM_HEAD,-1)
                 st.set("cond","11")
                 st.playSound("ItemSound.quest_itemget")
             else :
                 htmltext = "30617-04a.htm"
     return htmltext

 def onKill (self,npc,st):
     npcId = npc.getNpcId()
     cond = st.getInt("cond")
     if cond==2 :
         if npcId in range(20496,20500) and st.getQuestItemsCount(BLADE_MOLD)<20 :
             st.getPcSpawn().addSpawn(27190)
         elif npcId==27190 :
             st.dropQuestItems(BLADE_MOLD,1,20,CHANCE,1)
     elif cond==5 and npcId==20062 :
         st.dropQuestItems(RANGERS_REPORT1,1,1,CHANCE2,0)
         st.dropQuestItems(RANGERS_REPORT2,1,1,CHANCE21,0)
         st.dropQuestItems(RANGERS_REPORT3,1,1,CHANCE22,0)
         st.dropQuestItems(RANGERS_REPORT4,1,1,CHANCE23,0)
     elif cond==6 and npcId==20438 :
         st.dropQuestItems(WEAPON_TRADE_CONTRACT,1,1,CHANCE24,0)
         st.dropQuestItems(ATTACK_DIRECTIVES,1,1,CHANCE25,0)
     elif cond==10 and npcId==20066 :
         st.dropQuestItems(OL_MAHUM_HEAD,1,30,CHANCE3,1)
     return

QUEST       = Quest(171,"171_ActsOfEvil","Acts of Evil")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED     = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30381)

CREATED.addTalkId(30381)
STARTED.addTalkId(30381)
COMPLETED.addTalkId(30381)

STARTED.addTalkId(30207)
STARTED.addTalkId(30420)
STARTED.addTalkId(30425)
STARTED.addTalkId(30437)
STARTED.addTalkId(30617)

STARTED.addQuestDrop(30381,RANGERS_REPORT1,1)
STARTED.addQuestDrop(30381,RANGERS_REPORT2,1)
STARTED.addQuestDrop(30381,RANGERS_REPORT3,1)
STARTED.addQuestDrop(30381,RANGERS_REPORT4,1)
STARTED.addQuestDrop(30381,OL_MAHUM_HEAD,1)
STARTED.addQuestDrop(30381,CARGOBOX,1)
STARTED.addQuestDrop(30381,TYRAS_BILL,1)
STARTED.addQuestDrop(30381,CERTIFICATE,1)
STARTED.addQuestDrop(30381,BLADE_MOLD,1)
STARTED.addQuestDrop(30381,WEAPON_TRADE_CONTRACT,1)

for i in range(20494,20500)+[20062,20066,27190,20438] :
    STARTED.addKillId(i)

print "importing quests: 171: Acts of Evil"
