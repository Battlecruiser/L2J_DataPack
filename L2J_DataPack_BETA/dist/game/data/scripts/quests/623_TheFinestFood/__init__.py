# The Finest Food - v0.1 by disKret & DrLecter
import sys
from com.l2jserver import Config
from com.l2jserver.util import Rnd
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest import Quest as JQuest

qn = "623_TheFinestFood"

#NPC
JEREMY = 31521

#ITEMS
LEAF_OF_FLAVA,BUFFALO_MEAT,ANTELOPE_HORN = range(7199,7202)

#MOBS, DROPS, CHANCES & REWARDS
BUFFALO,FLAVA,ANTELOPE = [ 21315,21316,21318 ]
DROPLIST = {BUFFALO:[BUFFALO_MEAT,99],FLAVA:[LEAF_OF_FLAVA,99],ANTELOPE:[ANTELOPE_HORN,99]}
REWARDS = [[6849,25000,0,11],[6847,65000,12,23],[6851,25000,24,33],[0,73000,34,100]]

#needed count

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = range(7199,7202)

 def onEvent (self,event,st) :
   cond = st.getInt("cond")
   htmltext = event
   leaf = st.getQuestItemsCount(LEAF_OF_FLAVA)
   meat = st.getQuestItemsCount(BUFFALO_MEAT)
   horn = st.getQuestItemsCount(ANTELOPE_HORN)
   if event == "31521-03.htm" and cond == 0 :
     if st.getPlayer().getLevel() >= 71 :
        st.set("cond","1")
        st.setState(State.STARTED)
        st.playSound("ItemSound.quest_accept")
     else :
        htmltext = "31521-02.htm"
        st.exitQuest(1)
   elif event == "31521-07.htm" :
     if cond == 2 and leaf == meat == horn == 100 :
        htmltext = "31521-06.htm"
        st.playSound("ItemSound.quest_finish")
        random = self.getRandom(100)
        i = 0
        while i < len(REWARDS) :
            item,adena,chance,chance2=REWARDS[i]
            if chance<=random<= chance2 :
              break
            i = i+1
        st.giveItems(57,adena)
        if item :
           st.giveItems(item,1)
        else :
           st.addExpAndSp(230000,18250)
        st.takeItems(LEAF_OF_FLAVA,-1)
        st.takeItems(BUFFALO_MEAT,-1)
        st.takeItems(ANTELOPE_HORN,-1)
        st.exitQuest(1)
   return htmltext

 def onTalk (self,npc,player) :
   htmltext = Quest.getNoQuestMsg(player)
   st = player.getQuestState(qn)
   if st :
       cond = st.getInt("cond")
       leaf = st.getQuestItemsCount(LEAF_OF_FLAVA)
       meat = st.getQuestItemsCount(BUFFALO_MEAT)
       horn = st.getQuestItemsCount(ANTELOPE_HORN)
       if cond == 0 :
          htmltext = "31521-01.htm"
       elif st.getState() == State.STARTED :
           if cond == 1 :
              htmltext = "31521-05.htm"
           elif cond == 2 and leaf == meat == horn == 100 :
              htmltext = "31521-04.htm"
   return htmltext

 def onKill(self,npc,player,isPet):
   partyMember1 = self.getRandomPartyMember(player, 1)
   partyMember2 = self.getRandomPartyMemberState(player, State.COMPLETED)
   if not partyMember1 and not partyMember2 : return
   partyMember = partyMember1
   item,chance = DROPLIST[npc.getNpcId()]
   dropchance = Rnd.get(100)
   if dropchance  < chance:
    # player who has State.COMPLETED up to 2 out of 3 item collections may consume the party drop
    if partyMember2 :
      if Rnd.get(100) <= 66:
         return
      else :
         partyMember = partyMember1
    st = partyMember.getQuestState(qn)
    if st :
      if st.getState() == State.STARTED :
         count = st.getQuestItemsCount(item)
         if st.getInt("cond") == 1 and count < 100 :
            numItems, chance = divmod(chance*Config.RATE_QUEST_DROP,100)
            if dropchance  < chance:
               numItems += 1
            if count + numItems >= 100 :
              numItems = 100 - count
            if numItems != 0 :
              st.giveItems(item,int(numItems))
              if st.getQuestItemsCount(LEAF_OF_FLAVA) == st.getQuestItemsCount(BUFFALO_MEAT) == st.getQuestItemsCount(ANTELOPE_HORN) == 100 :
                 st.set("cond","2")
                 st.playSound("ItemSound.quest_middle")
              else :
                 st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(623,qn,"The Finest Food")

QUEST.addStartNpc(JEREMY)
QUEST.addTalkId(JEREMY)

for mob in DROPLIST.keys() :
  QUEST.addKillId(mob)