# Linnaeus Special Bait - a seamless merge from Next and DooMita contributions
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "53_LinnaeusSpecialBait"

LINNAEUS = 31577
CRIMSON_DRAKE = 20670
CRIMSON_DRAKE_HEART = 7624
FLAMING_FISHING_LURE = 7613
#Drop chance
CHANCE = 50
#Custom setting: wether or not to check for fishing skill level?
#default False to require fishing skill level, any other value to ignore fishing
#and evaluate char level only.
ALT_IGNORE_FISHING=False

def fishing_level(player):
    if ALT_IGNORE_FISHING :
       level=20
    else :
       level = player.getSkillLevel(1315)
       for effect in player.getAllEffects():
          if effect.getSkill().getId() == 2274:
            level = int(effect.getSkill().getPower(player))
            break
    return level

class Quest (JQuest):

    def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

    def onEvent (self,event,st):
        htmltext = event
        if event == "31577-1.htm":
           st.setState(STARTED)
           st.set("cond","1")
           st.playSound("ItemSound.quest_accept")
        elif event == "31577-5.htm":
           cond = st.getInt("cond")
           if cond == 2 and st.getQuestItemsCount(CRIMSON_DRAKE_HEART) == 100:
              st.giveItems(FLAMING_FISHING_LURE, 4)
              st.takeItems(CRIMSON_DRAKE_HEART, 100)                
              st.setState(COMPLETED)
              st.unset("cond") # we dont need it in db if quest is already completed
              st.playSound("ItemSound.quest_finish")
           else :
              htmltext = "31577-4.htm"
        return htmltext

    def onTalk (self,npc,player):
        st = player.getQuestState(qn)
        htmltext="<html><body>I have nothing to say you.</body></html>"
        if not st: return htmltext
        id = st.getState()
        if id == COMPLETED: htmltext = "<html><head><body>This quest have already been completed.</body></html>"           
        elif id == CREATED :
           if st.getPlayer().getLevel() > 59 and fishing_level(player) > 19 :
              htmltext= "31577-0.htm"
           else:
              st.exitQuest(1)
              htmltext= "31577-0a.htm"
        elif id == STARTED:
           if st.getInt("cond") == 1:
               htmltext = "31577-4.htm"
           else :
               htmltext = "31577-2.htm"
        return htmltext

    def onKill (self,npc,player):
        partyMember = self.getRandomPartyMember(player,"1")
        if not partyMember: return
        st = partyMember.getQuestState(qn)
        if not st: return
        count = st.getQuestItemsCount(CRIMSON_DRAKE_HEART)
        if count < 100 and st.getRandom(100) < CHANCE:
          st.giveItems(CRIMSON_DRAKE_HEART, 1)                
          if count == 99 :
            st.set("cond","2")
            st.playSound("ItemSound.quest_middle")
          else:
            st.playSound("ItemSound.quest_itemget")
        return

QUEST       = Quest(53, qn, "Linnaeus Special Bait")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(LINNAEUS)
QUEST.addTalkId(LINNAEUS)

QUEST.addKillId(CRIMSON_DRAKE)
STARTED.addQuestDrop(LINNAEUS,CRIMSON_DRAKE_HEART,1)

print "importing quests: 53: Linnaeus Special Bait"
