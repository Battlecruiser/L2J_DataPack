#Made by Kerb
#Rewrited to Epilogue by Gigiikun
import sys

from com.l2jserver import Config
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest
from com.l2jserver.util import Rnd

qn = "645_GhostsOfBatur"

#Drop rate
DROP_CHANCE = 5
DROP_CHANCE_GHOST_COMMANDER = 33
#Npc
KARUDA = 32017
#Items
CURSED_BURIAL = 14861
#Rewards
REWARDS={
    "S80":[500,[9967,9968,9969,9970,9971,9972,9973,9974,9975,10544,10545]],
    "ORI":[12,[9630]],
    "LEO":[8,[9628]],
    "ADA":[15,[9629]]
    }
#Mobs
MOBS = [ 22703, 22704, 22705, 22706 ]
GHOST_COMMANDER = 22707

class Quest (JQuest) :
 def __init__(self,id,name,descr):
    JQuest.__init__(self,id,name,descr)
    self.questItemIds = [CURSED_BURIAL]

 def onAdvEvent (self,event,npc, player) :
   htmltext = event
   st = player.getQuestState(qn)
   if not st : return
   if event == "32017-03.htm" :
      if st.getPlayer().getLevel() < 80 :
         htmltext = "32017-02.htm"
         st.exitQuest(1)
      else :
         st.set("cond","1")
         st.setState(State.STARTED)
         st.playSound("ItemSound.quest_accept")
   elif event in REWARDS.keys() :
      qty,item = REWARDS[event]
      if st.getQuestItemsCount(CURSED_BURIAL) >= qty :
         st.takeItems(CURSED_BURIAL,qty)
         if len(item) > 1 :
            itemId = item[Rnd.get(len(item))]
         else :
            itemId = item[0]
         st.rewardItems(itemId,1)
         htmltext = "32017-05c.htm"
      else :
         htmltext = "32017-07.htm"
   elif event == "32017-08.htm" :
      st.exitQuest(1)
   return htmltext

 def onTalk (self,npc,player):
   htmltext = Quest.getNoQuestMsg(player)
   st = player.getQuestState(qn)
   if not st : return htmltext
   npcId = npc.getNpcId()
   id = st.getState()
   cond = st.getInt("cond")
   if cond == 0 :
      htmltext = "32017-01.htm"
   elif cond == 1 :
      if st.getQuestItemsCount(CURSED_BURIAL) > 0 :
         htmltext = "32017-05b.htm"
      else :
         htmltext = "32017-05a.htm"
   else :
      htmltext = "32017-02.htm"
      st.exitQuest(1)
   return htmltext

 def onKill(self,npc,player,isPet):
  partyMember = self.getRandomPartyMember(player,"1")
  if not partyMember: return
  st = partyMember.getQuestState(qn)
  if st :
    count = st.getQuestItemsCount(CURSED_BURIAL)
    if st.getInt("cond") == 1 :
      chance = DROP_CHANCE
      if npc.getNpcId() == GHOST_COMMANDER:
        chance = DROP_CHANCE_GHOST_COMMANDER
      numItems, chance = divmod(chance,100)
      if st.getRandom(100) < chance :
         numItems += 1
      if numItems :
         if int(count + numItems)/500 > int(count)/500 :
            st.playSound("ItemSound.quest_middle")
         else:
            st.playSound("ItemSound.quest_itemget")
         st.giveItems(CURSED_BURIAL,int(numItems))
  return

QUEST       = Quest(645, qn, "Ghosts of Batur")

QUEST.addStartNpc(KARUDA)
QUEST.addTalkId(KARUDA)

for i in MOBS :
  QUEST.addKillId(i)