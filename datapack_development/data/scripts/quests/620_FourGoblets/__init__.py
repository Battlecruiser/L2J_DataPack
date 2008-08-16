import sys
from net.sf.l2j.gameserver.model.quest          import State
from net.sf.l2j.gameserver.model.quest          import QuestState
from net.sf.l2j.gameserver.model.quest.jython   import QuestJython as JQuest
from net.sf.l2j.gameserver.instancemanager      import FourSepulchersManager

qn = "620_FourGoblets"

#NPC
NAMELESS_SPIRIT = 31453

GHOST_OF_WIGOTH_1 = 31452
GHOST_OF_WIGOTH_2 = 31454

CONQ_SM = 31921
EMPER_SM = 31922
SAGES_SM = 31923
JUDGE_SM = 31924

GHOST_CHAMBERLAIN_1 = 31919
GHOST_CHAMBERLAIN_2 = 31920

#ITEMS
ENTRANCE_PASS = 7075
GRAVE_PASS = 7261
GOBLETS = [7256,7257,7258,7259]
RELIC = 7254
SEALED_BOX = 7255

#REWARDS
ANTIQUE_BROOCH = 7262
REWARDS = [57,81,151,959,1895,2500,4040,4042,4043,5529,5545,5546]
RCP_REWARDS = [ 6881,6883,6885,6887,6891,6893,6895,6897,6899,7580 ]

class Quest (JQuest) :

  def __init__(self,id,name,descr):
      JQuest.__init__(self,id,name,descr)
      self.questItemIds = [ANTIQUE_BROOCH,SEALED_BOX,7256,7257,7258,7259,GRAVE_PASS]

  def onTalk (Self,npc,player) :
    htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
    st = player.getQuestState(qn)
    id = st.getState()
    if id == State.CREATED :
      st.set("cond","0")
    npcId = npc.getNpcId()
    if npcId == NAMELESS_SPIRIT:
      if int(st.get("cond")) == 0 :
        if st.getPlayer().getLevel() >= 74 :
          htmltext = "31453-1.htm"
        else :
          htmltext = "31453-12.htm"
          st.exitQuest(1)
      elif int(st.get("cond")) == 1 :
        if st.getQuestItemsCount(GOBLETS[0]) >= 1 and st.getQuestItemsCount(GOBLETS[1]) >= 1 and st.getQuestItemsCount(GOBLETS[2]) >= 1 and st.getQuestItemsCount(GOBLETS[3]) >= 1 :
          htmltext = "31453-15.htm"
        else :
          htmltext = "31453-14.htm"
      elif int(st.get("cond")) == 2 :
          htmltext = "31453-17.htm"
    elif npcId == GHOST_OF_WIGOTH_1 :
      if st.getInt("cond") == 1:
         if st.getQuestItemsCount(GOBLETS[0]) + st.getQuestItemsCount(GOBLETS[1]) + st.getQuestItemsCount(GOBLETS[2]) + st.getQuestItemsCount(GOBLETS[3]) == 1 :
            htmltext = "31452-1.htm"
         elif st.getQuestItemsCount(GOBLETS[0]) + st.getQuestItemsCount(GOBLETS[1]) + st.getQuestItemsCount(GOBLETS[2]) + st.getQuestItemsCount(GOBLETS[3]) > 1 :
            htmltext = "31452-2.htm"
      elif st.getInt("cond") == 2:
         htmltext = "31452-2.htm"
    elif npcId == GHOST_OF_WIGOTH_2 :
      if st.getQuestItemsCount(RELIC) >= 1000 :
         if st.getQuestItemsCount(SEALED_BOX) >= 1 :
             if st.getQuestItemsCount(GOBLETS[0]) >= 1 and st.getQuestItemsCount(GOBLETS[1]) >= 1 and st.getQuestItemsCount(GOBLETS[2]) >= 1 and st.getQuestItemsCount(GOBLETS[3]) >= 1 :
                htmltext = "31454-4.htm"
             else :
                if st.getQuestItemsCount(GOBLETS[0]) + st.getQuestItemsCount(GOBLETS[1]) + st.getQuestItemsCount(GOBLETS[2]) + st.getQuestItemsCount(GOBLETS[3]) > 1 :
                   htmltext = "31454-8.htm"
                else :
                   htmltext = "31454-12.htm"
         else :
             if st.getQuestItemsCount(GOBLETS[0]) >= 1 and st.getQuestItemsCount(GOBLETS[1]) >= 1 and st.getQuestItemsCount(GOBLETS[2]) >= 1 and st.getQuestItemsCount(GOBLETS[3]) >= 1 :
                htmltext = "31454-3.htm"
             else :
                if st.getQuestItemsCount(GOBLETS[0]) + st.getQuestItemsCount(GOBLETS[1]) + st.getQuestItemsCount(GOBLETS[2]) + st.getQuestItemsCount(GOBLETS[3]) > 1 :
                   htmltext = "31454-7.htm"
                else :
                   htmltext = "31454-11.htm"
      else :
         if st.getQuestItemsCount(SEALED_BOX) >= 1 :
             if st.getQuestItemsCount(GOBLETS[0]) >= 1 and st.getQuestItemsCount(GOBLETS[1]) >= 1 and st.getQuestItemsCount(GOBLETS[2]) >= 1 and st.getQuestItemsCount(GOBLETS[3]) >= 1 :
                htmltext = "31454-2.htm"
             else :
                if st.getQuestItemsCount(GOBLETS[0]) + st.getQuestItemsCount(GOBLETS[1]) + st.getQuestItemsCount(GOBLETS[2]) + st.getQuestItemsCount(GOBLETS[3]) > 1 :
                   htmltext = "31454-6.htm"
                else :
                   htmltext = "31454-10.htm"
         else :
             if st.getQuestItemsCount(GOBLETS[0]) >= 1 and st.getQuestItemsCount(GOBLETS[1]) >= 1 and st.getQuestItemsCount(GOBLETS[2]) >= 1 and st.getQuestItemsCount(GOBLETS[3]) >= 1 :
                htmltext = "31454-1.htm"
             else :
                if st.getQuestItemsCount(GOBLETS[0]) + st.getQuestItemsCount(GOBLETS[1]) + st.getQuestItemsCount(GOBLETS[2]) + st.getQuestItemsCount(GOBLETS[3]) > 1 :
                   htmltext = "31454-5.htm"
                else :
                   htmltext = "31454-9.htm"

    elif npcId == CONQ_SM :
      htmltext = "31921-E.htm"
    elif npcId == EMPER_SM :
      htmltext = "31922-E.htm"
    elif npcId == SAGES_SM :
      htmltext = "31923-E.htm"
    elif npcId == JUDGE_SM :
      htmltext = "31924-E.htm"
    elif npcId == GHOST_CHAMBERLAIN_1 :
      htmltext = "31919-1.htm"
    return htmltext

  def onKill (self,npc,player,isPet) :
    st = player.getQuestState(qn)
    npcId = npc.getNpcId()
    if st:
      if int(st.get("cond")) == 1 or int(st.get("cond")) == 2 :
        if npcId in range(18120,18256) :
          if st.getRandom(100) < 30 :
            st.giveItems(SEALED_BOX,1)
            st.playSound("ItemSound.quest_itemget")
      return

  def onAdvEvent (self,event,npc,player) :
    htmltext = event
    st = player.getQuestState(qn)
    htmltext = event
    if event == "Enter" : 
      FourSepulchersManager.getInstance().tryEntry(npc,player)
      return
    if not st : return
    elif event == "accept" :
      if int(st.get("cond")) == 0 :
        if st.getPlayer().getLevel() >= 74 :
          st.setState(State.STARTED)
          st.playSound("ItemSound.quest_accept")
          htmltext = "31453-13.htm"
          st.set("cond","1")
        else :
          htmltext = "31453-12.htm"
          st.exitQuest(1)
    elif event == "11" :
      if st.getQuestItemsCount(SEALED_BOX) >= 1 :
        htmltext = "31454-13.htm"
        st.takeItems(SEALED_BOX,1)
        reward = 0
        if st.getRandom(1000000) < 700000 :
          cnt = 1370 + st.getRandom(1374)
          st.giveItems(REWARDS[0],cnt)
          reward = 1
        if st.getRandom(1000000) < 2 :
          st.giveItems(REWARDS[1],1)
          reward = 1
        if st.getRandom(1000000) < 2 :
          st.giveItems(REWARDS[2],1)
          reward = 1
        if st.getRandom(1000000) < 8 :
          st.giveItems(REWARDS[3],1)
          reward = 1
        if st.getRandom(1000000) < 54858 :
          st.giveItems(REWARDS[4],1)
          reward = 1
        if st.getRandom(1000000) < 2 :
          st.giveItems(REWARDS[5],1)
          reward = 1
        if st.getRandom(1000000) < 3841 :
          st.giveItems(REWARDS[6],1)
          reward = 1
        if st.getRandom(1000000) < 3201 :
          st.giveItems(REWARDS[7],1)
          reward = 1
        if st.getRandom(1000000) < 6401 :
          st.giveItems(REWARDS[8],1)
          reward = 1
        if st.getRandom(1000000) < 440 :
          st.giveItems(REWARDS[9],1)
          reward = 1
        if st.getRandom(1000000) < 440 :
          st.giveItems(REWARDS[10],1)
          reward = 1
        if st.getRandom(1000000) < 483 :
          st.giveItems(REWARDS[11],1)
          reward = 1
        if reward == 0 :
          if st.getRandom(2) == 0 :
             htmltext = "31454-14.htm"
          else :
             htmltext = "31454-15.htm"
    elif event == "12" :
      if st.getQuestItemsCount(GOBLETS[0]) >= 1 and st.getQuestItemsCount(GOBLETS[1]) >= 1 and st.getQuestItemsCount(GOBLETS[2]) >= 1 and st.getQuestItemsCount(GOBLETS[3]) >= 1 :
        st.takeItems(GOBLETS[0],-1)
        st.takeItems(GOBLETS[1],-1)
        st.takeItems(GOBLETS[2],-1)
        st.takeItems(GOBLETS[3],-1)
        st.giveItems(ANTIQUE_BROOCH,1)
        st.set("cond","2")
        st.playSound("ItemSound.quest_finish")
        htmltext = "31453-16.htm"
      else :
        htmltext = "31453-14.htm"
    elif event == "13" :
      st.playSound("ItemSound.quest_finish")
      st.exitQuest(1)
      htmltext = "31453-18.htm"
    elif event == "14" :
      htmltext = "31453-13.htm"
      if st.getInt("cond") == 2:
         htmltext = "31453-19.htm"
    # Ghost Chamberlain of Elmoreden: Teleport to 4th sepulcher
    elif event == "15" :
      if st.getQuestItemsCount(ANTIQUE_BROOCH) >= 1 :
        st.getPlayer().teleToLocation(178298,-84574,-7216)
        htmltext = None
      elif st.getQuestItemsCount(GRAVE_PASS) >= 1 :
        st.takeItems(GRAVE_PASS,1)
        st.getPlayer().teleToLocation(178298,-84574,-7216)
        htmltext = None
      else :
        htmltext = ""+str(npc.getNpcId())+"-0.htm"
    # Ghost Chamberlain of Elmoreden: Teleport to Imperial Tomb entrance
    elif event == "16" :
      if st.getQuestItemsCount(ANTIQUE_BROOCH) >= 1 :
        st.getPlayer().teleToLocation(186942,-75602,-2834)
        htmltext = None
      elif st.getQuestItemsCount(GRAVE_PASS) >= 1 :
        st.takeItems(GRAVE_PASS,1)
        st.getPlayer().teleToLocation(186942,-75602,-2834)
        htmltext = None
      else :
        htmltext = ""+str(npc.getNpcId())+"-0.htm"
    # Teleport to Pilgrims Temple
    elif event == "17" :
      if st.getQuestItemsCount(ANTIQUE_BROOCH) >= 1 :
        st.getPlayer().teleToLocation(169590,-90218,-2914)
      else :
        st.takeItems(GRAVE_PASS,1)
        st.getPlayer().teleToLocation(169590,-90218,-2914)
      htmltext = "31452-6.htm"
    elif event == "18" :
      if st.getQuestItemsCount(GOBLETS[0]) + st.getQuestItemsCount(GOBLETS[1]) + st.getQuestItemsCount(GOBLETS[2]) + st.getQuestItemsCount(GOBLETS[3]) < 3 :
        htmltext = "31452-3.htm"
      elif st.getQuestItemsCount(GOBLETS[0]) + st.getQuestItemsCount(GOBLETS[1]) + st.getQuestItemsCount(GOBLETS[2]) + st.getQuestItemsCount(GOBLETS[3]) == 3 :
        htmltext = "31452-4.htm"
      elif st.getQuestItemsCount(GOBLETS[0]) + st.getQuestItemsCount(GOBLETS[1]) + st.getQuestItemsCount(GOBLETS[2]) + st.getQuestItemsCount(GOBLETS[3]) >= 4 :
        htmltext = "31452-5.htm"
    elif event == "19" :
      if st.getQuestItemsCount(SEALED_BOX) >= 1 :
        htmltext = "31919-3.htm"
        st.takeItems(SEALED_BOX,1)
        reward = 0
        if st.getRandom(1000000) < 350000 :
          cnt = 1370 + st.getRandom(1374)
          st.giveItems(REWARDS[0],cnt)
          reward = 1
        if st.getRandom(1000000) < 1 :
          st.giveItems(REWARDS[1],1)
          reward = 1
        if st.getRandom(1000000) < 1 :
          st.giveItems(REWARDS[2],1)
          reward = 1
        if st.getRandom(1000000) < 4 :
          st.giveItems(REWARDS[3],1)
          reward = 1
        if st.getRandom(1000000) < 27429 :
          st.giveItems(REWARDS[4],1)
          reward = 1
        if st.getRandom(1000000) < 1 :
          st.giveItems(REWARDS[5],1)
          reward = 1
        if st.getRandom(1000000) < 1921 :
          st.giveItems(REWARDS[6],1)
          reward = 1
        if st.getRandom(1000000) < 1601 :
          st.giveItems(REWARDS[7],1)
          reward = 1
        if st.getRandom(1000000) < 3201 :
          st.giveItems(REWARDS[8],1)
          reward = 1
        if st.getRandom(1000000) < 220 :
          st.giveItems(REWARDS[9],1)
          reward = 1
        if st.getRandom(1000000) < 220 :
          st.giveItems(REWARDS[10],1)
          reward = 1
        if st.getRandom(1000000) < 241 :
          st.giveItems(REWARDS[11],1)
          reward = 1
        if reward == 0 :
          if st.getRandom(2) == 0 :
             htmltext = "31919-4.htm"
          else :
             htmltext = "31919-5.htm"
      else :
        htmltext = "31919-6.htm"
    elif event.isdigit() and int(event) in RCP_REWARDS :
      st.takeItems(RELIC,1000)
      st.giveItems(int(event),1)
      htmltext = "31454-17.htm"
    return htmltext

QUEST       = Quest(620,qn,"Four Goblets")

QUEST.addStartNpc(NAMELESS_SPIRIT)

QUEST.addTalkId(NAMELESS_SPIRIT)

for npcTalkId in [GHOST_OF_WIGOTH_1,GHOST_OF_WIGOTH_2,CONQ_SM,EMPER_SM,SAGES_SM,JUDGE_SM,GHOST_CHAMBERLAIN_1,GHOST_CHAMBERLAIN_2] :
  QUEST.addTalkId(npcTalkId)

for npcStartId in [CONQ_SM,EMPER_SM,SAGES_SM,JUDGE_SM,GHOST_CHAMBERLAIN_1,GHOST_CHAMBERLAIN_2] :
  QUEST.addStartNpc(npcStartId)

for npcKillId in range(18120,18256) :
  QUEST.addKillId(npcKillId)