#Made by Emperorc
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "607_ProveYourCourage_Ketra"

#NPC
Kadun = 31370
Shadith = 25309

#Quest Items
Shadith_Head = 7235
Valor_Totem = 7219
Ketra_Alliance_Three = 7213

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "31370-04.htm" :
       if st.getPlayer().getAllianceWithVarkaKetra() == 3 and st.getQuestItemsCount(Ketra_Alliance_Three) :
            if st.getPlayer().getLevel() >= 75 :
                    st.set("cond","1")
                    st.setState(STARTED)
                    st.playSound("ItemSound.quest_accept")
                    htmltext = "31370-04.htm"
            else :
                htmltext = "31370-03.htm"
                st.exitQuest(1)
       else :
            htmltext = "31370-02.htm"
            st.exitQuest(1)
   elif event == "31370-07.htm" :
       st.takeItems(Shadith_Head,-1)
       st.giveItems(Valor_Totem,1)
       st.addExpAndSp(10000,0)
       st.playSound("ItemSound.quest_finish")
       htmltext = "31370-07.htm"
       st.exitQuest(1)
   return htmltext

 def onTalk (self,npc,player):
    htmltext = "<html><head><body>I have nothing to say you</body></html>"
    st = player.getQuestState(qn)
    if st :
      npcId = npc.getNpcId()
      cond = st.getInt("cond")
      Head = st.getQuestItemsCount(Shadith_Head)
      Valor = st.getQuestItemsCount(Valor_Totem)
      if npcId == Kadun :
          if Valor == 0 :
              if Head == 0:
                  if cond != 1 :
                      htmltext = "31370-01.htm"
                  else:
                      htmltext = "31370-06.htm"
              else :
                  htmltext = "31370-05.htm"
          #else:
              #htmltext="<html><head><body>This quest has already been completed</body></html>"
    return htmltext

 def onKill (self,npc,player):
    st = player.getQuestState(qn)
    if st :
         if st.getState() == STARTED :
            npcId = npc.getNpcId()
            cond = st.getInt("cond")
            if npcId == Shadith :
                if st.getPlayer().isAlliedWithKetra() :
                    if cond == 1:
                        if st.getPlayer().getAllianceWithVarkaKetra() == 3 and st.getQuestItemsCount(Ketra_Alliance_Three) :
                            st.giveItems(Shadith_Head,1)
                            st.set("cond","2")
    return

QUEST       = Quest(607,qn,"Prove Your Courage!")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(Kadun)
QUEST.addTalkId(Kadun)

STARTED.addQuestDrop(Shadith,Shadith_Head,1)
QUEST.addKillId(Shadith)

print "importing quests: 607: Prove Your Courage! (Ketra)" 
