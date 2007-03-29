# Created by Emperorc
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
from net.sf.l2j.gameserver.datatables import SkillTable

qn = "6050_KetraOrcSupport"

Jumara = 31375
Asefa = 31372
Horn = 7186
#"event number":[Buff Id,Buff Level,Cost]
BUFF={
"1":[4359,1,2],#Focus: Requires 2 Buffalo Horns
"2":[4360,1,2],#Death Whisper: Requires 2 Buffalo Horns
"3":[4345,1,3],#Might: Requires 3 Buffalo Horns
"4":[4355,1,3],#Acumen: Requires 3 Buffalo Horns
"5":[4352,1,3],#Berserker: Requires 3 Buffalo Horns
"6":[4354,1,3],#Vampiric Rage: Requires 3 Buffalo Horns
"7":[4356,1,6],#Empower: Requires 6 Buffalo Horns
"8":[4357,1,6],#Haste: Requires 6 Buffalo Horns
}
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
    if event in BUFF.keys() :
        skillId,level,horns=BUFF[event]
        if st.getQuestItemsCount(Horn) >= horns :
            a = st.getPlayer().getCurrentMp()
            st.takeItems(Horn,horns)
            st.getPlayer().setTarget(st.getPlayer())
            st.getPlayer().doCast(SkillTable.getInstance().getInfo(skillId,level))
            st.getPlayer().setCurrentMp(a)
            htmltext = "a4.htm"
    return

 def onTalk (self,npc,player):
    htmltext = "<html><head><body>I have nothing to say you</body></html>"
    st = player.getQuestState(qn)
    if not st : return htmltext
    npcId = npc.getNpcId()
    id = st.getState()
    Alevel = st.getPlayer().getAllianceWithVarkaKetra()
    Horns = st.getQuestItemsCount(Horn)
    if npcId == Jumara :
        if Alevel == 2 :
           htmltext = "1.htm"
        elif Alevel == 3 or Alevel == 4 :
            htmltext = "2.htm"
        elif Alevel == 5 :
            htmltext = "3.htm"
        else :
            htmltext = "no.htm"
    elif npcId == Asefa :
        if Alevel < 1 :
            htmltext = "a3.htm"
        elif Alevel < 3 and Alevel > 0:
            htmltext = "a1.htm"
        elif Alevel > 2 :
            if Horns :
                htmltext = "a4.htm"
            else :
                htmltext = "a2.htm"
    return htmltext

QUEST       = Quest(6050, qn, "custom")
CREATED     = State('Start', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(Jumara)
QUEST.addTalkId(Jumara)
QUEST.addStartNpc(Asefa)
QUEST.addTalkId(Asefa)

print "importing quests: 6050: Ketra Orc Support"