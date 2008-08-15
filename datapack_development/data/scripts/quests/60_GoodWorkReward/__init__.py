# Made by Kerberos v1.0 on 2008/07/31
# this script is part of the Official L2J Datapack Project.
# Visit http://forum.l2jdp.com for more details.

import sys
from net.sf.l2j.gameserver.ai import CtrlIntention
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
from net.sf.l2j.gameserver.network.serverpackets import NpcSay

qn = "60_GoodWorkReward"

BYPASS = {
1:"<a action=\"bypass -h Quest 60_GoodWorkReward WL\">Warlord.</a><br><a action=\"bypass -h Quest 60_GoodWorkReward GL\">Gladiator.</a>",
4:"<a action=\"bypass -h Quest 60_GoodWorkReward PA\">Paladin.</a><br><a action=\"bypass -h Quest 60_GoodWorkReward DA\">Dark Avenger.</a>",
7:"<a action=\"bypass -h Quest 60_GoodWorkReward TH\">Treasure Hunter.</a><br><a action=\"bypass -h Quest 60_GoodWorkReward HK\">Hawkeye.</a>",
11:"<a action=\"bypass -h Quest 60_GoodWorkReward SC\">Sorcerer.</a><br><a action=\"bypass -h Quest 60_GoodWorkReward NM\">Necromancer.</a><br><a action=\"bypass -h Quest 60_GoodWorkReward WA\">Warlock.</a>",
15:"<a action=\"bypass -h Quest 60_GoodWorkReward BS\">Bishop.</a><br><a action=\"bypass -h Quest 60_GoodWorkReward PP\">Prophet.</a>",
19:"<a action=\"bypass -h Quest 60_GoodWorkReward TK\">Temple Knight.</a><br><a action=\"bypass -h Quest 60_GoodWorkReward SS\">Swordsinger.</a>",
22:"<a action=\"bypass -h Quest 60_GoodWorkReward PW\">Plainswalker.</a><br><a action=\"bypass -h Quest 60_GoodWorkReward SR\">Silver Ranger.</a>",
26:"<a action=\"bypass -h Quest 60_GoodWorkReward SP\">Spellsinger.</a><br><a action=\"bypass -h Quest 60_GoodWorkReward ES\">Elemental Summoner.</a>",
29:"<a action=\"bypass -h Quest 60_GoodWorkReward EE\">Elven Elder.</a>",
32:"<a action=\"bypass -h Quest 60_GoodWorkReward SK\">Shillien Knight.</a><br><a action=\"bypass -h Quest 60_GoodWorkReward BD\">Blade Dancer.</a>",
35:"<a action=\"bypass -h Quest 60_GoodWorkReward AW\">Abyss Walker.</a><br><a action=\"bypass -h Quest 60_GoodWorkReward PR\">Phantom Ranger.</a>",
39:"<a action=\"bypass -h Quest 60_GoodWorkReward SH\">Spellhowler.</a><br><a action=\"bypass -h Quest 60_GoodWorkReward PS\">Phantom Summoner.</a>",
42:"<a action=\"bypass -h Quest 60_GoodWorkReward SE\">Shillien Elder.</a>",
45:"<a action=\"bypass -h Quest 60_GoodWorkReward DT\">Destroyer.</a>",
47:"<a action=\"bypass -h Quest 60_GoodWorkReward TR\">Tyrant.</a>",
50:"<a action=\"bypass -h Quest 60_GoodWorkReward OL\">Overlord.</a><br><a action=\"bypass -h Quest 60_GoodWorkReward WC\">Warcryer.</a>",
54:"<a action=\"bypass -h Quest 60_GoodWorkReward BH\">Bounty Hunter.</a>",
57:"<a action=\"bypass -h Quest 60_GoodWorkReward WS\">Warsmith.</a>",
125:"<a action=\"bypass -h Quest 60_GoodWorkReward BE\">Berserker.</a><br><a action=\"bypass -h Quest 60_GoodWorkReward MB\">Soul Breaker.</a>",
126:"<a action=\"bypass -h Quest 60_GoodWorkReward AB\">Arbalester.</a><br><a action=\"bypass -h Quest 60_GoodWorkReward FB\">Soul Breaker.</a>"
}

CLASSES = {
"AB":130,
"AW":36,
"BD":34,
"BE":127,
"BH":55,
"BS":16,
"DA":6,
"DT":46,
"EE":30,
"ES":28,
"FB":129,
"GL":2,
"HK":9,
"MB":128,
"NM":13,
"OL":51,
"PA":5,
"PP":17,
"PR":37,
"PS":41,
"PW":23,
"SC":12,
"SE":43,
"SH":40,
"SK":33,
"SP":27,
"SR":24,
"SS":21,
"TH":8,
"TK":20,
"TR":48,
"WA":14,
"WC":52,
"WL":3,
"WS":57
}

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [10867,10868]
     self.isNpcSpawned = 0

 def onEvent (self,event,st) :
    htmltext = event
    player = st.getPlayer()
    if event == "31435-03.htm" :
      st.set("cond","1")
      st.setState(State.STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "31435-05.htm" :
      st.set("cond","4")
      st.playSound("ItemSound.quest_middle")
    elif event == "31435-08.htm" :
      st.set("cond","9")
      st.playSound("ItemSound.quest_middle")
    elif event == "32487-02.htm" and self.isNpcSpawned == 0:
      npc = st.addSpawn(27340,72590,148100,-3312,1800000)
      npc.broadcastPacket(NpcSay(npc.getObjectId(),0,npc.getNpcId(),player.getName()+"! I must kill you. Blame your own curiosity."))
      npc.setRunning()
      npc.addDamageHate(st.getPlayer(),0,999)
      npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, st.getPlayer())
      self.isNpcSpawned = 1
    elif event == "32487-06.htm" :
      st.set("cond","8")
      st.playSound("ItemSound.quest_middle")
      st.takeItems(10868,-1)
    elif event == "30081-03.htm" :
      st.set("cond","5")
      st.playSound("ItemSound.quest_middle")
      st.takeItems(10867,-1)
    elif event == "30081-05.htm" :
      st.set("cond","6")
      st.playSound("ItemSound.quest_middle")
    elif event == "30081-08.htm" :
      if st.getQuestItemsCount(57) >= 3000000 :
         st.takeItems(57,3000000)
         st.giveItems(10868,1)
         st.set("cond","7")
         st.playSound("ItemSound.quest_middle")
      else :
         htmltext = "32435-07.htm"
    elif event == "31092-05.htm" :
      st.exitQuest(False)
      st.playSound("ItemSound.quest_finish")
      if player.getClassId().level() == 1 :
         text = BYPASS[player.getClassId().getId()]
         htmltext = "<html><body>Black Marketeer of Mammon:<br>Forget about the money!<br>I will help you complete the class transfer, which is far more valuable! Which class would you like to be? Choose one.<br>"+text+"</body></html>"
      else :
         htmltext = "<html><body>Black Marketeer of Mammon:<br>You have already 2nd occupation completed.</body></html>" #TODO: replace me with proper html
    elif event == "31092-06.htm" :
      text = BYPASS[player.getClassId().getId()]
      htmltext = "<html><body>Black Marketeer of Mammon:<br>If you are finished thinking, select one. Which class would you like to be?<br>"+text+"</body></html>"
    elif event in CLASSES.keys():
      if player.getLevel() >= 40:
         newclass=CLASSES[event]
         player.setClassId(newclass)
         player.setBaseClass(newclass)
         player.broadcastUserInfo()
         return
      else:
         htmltext = "<html><body>Black Marketeer of Mammon:<br>To change occupation, your level must be over 40. Come back after you finish thinking about it.</body></html>"
    return htmltext

 def onTalk (self,npc,player):
   htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   cond = st.getInt("cond")
   id = st.getState()
   if id == State.COMPLETED :
     if npcId == 31435 :
        htmltext = "<html><body>This quest has already been completed.</body></html>"
     elif npcId == 31092 :
        if player.getClassId().level() == 1 :
           htmltext = "31092-05.htm"
   if id == State.CREATED :
     if player.getLevel() < 39 :
       htmltext = "31435-00.htm"
       st.exitQuest(1)
     else :
       htmltext = "31435-01.htm"
   elif npcId == 31435 :
     if cond in [1,2]:
       htmltext = "31435-03.htm"
     elif cond == 3:
       htmltext = "31435-04.htm"
     elif cond in [4,5,6,7]:
       htmltext = "31435-06.htm"
     elif cond == 8:
       htmltext = "31435-07.htm"
     elif cond == 9:
       htmltext = "31435-09.htm"
       st.set("cond","10")
       st.playSound("ItemSound.quest_middle")
     elif cond == 10:
       htmltext = "31435-10.htm"
   elif npcId == 32487 :
     if cond == 1:
       htmltext = "32487-01.htm"
     elif cond == 2:
       htmltext = "32487-03.htm"
       st.set("cond","3")
       st.playSound("ItemSound.quest_middle")
     elif cond == 3:
       htmltext = "32487-04.htm"
     elif cond == 7:
       htmltext = "32487-05.htm"
     elif cond == 8:
       htmltext = "32487-06.htm"
   elif npcId == 30081 :
     if cond == 4:
       htmltext = "30081-01.htm"
     elif cond == 5:
       htmltext = "30081-04.htm"
     elif cond == 6:
       htmltext = "30081-06.htm"
     elif cond == 7:
       htmltext = "30081-09.htm"
   elif npcId == 31092 and cond == 10 :
       htmltext = "31092-01.htm"
   return htmltext

 def onKill(self,npc,player,isPet):
   st = player.getQuestState(qn)
   if not st : return
   if st.getState() != State.STARTED : return
   npcId = npc.getNpcId()
   cond = st.getInt("cond")
   if npcId == 27340 and cond == 1:
     self.isNpcSpawned = 0
     string = "You are strong. This was a mistake."
     if st.getRandom(1):
       string = "You have good luck. I shall return."
     npc.broadcastPacket(NpcSay(npc.getObjectId(),0,npc.getNpcId(),string))
     st.giveItems(10867,1)
     st.set("cond","2")
     st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(60,qn,"Good Work's Reward")

QUEST.addStartNpc(31435)
QUEST.addTalkId(30081)
QUEST.addTalkId(31092)
QUEST.addTalkId(31435)
QUEST.addTalkId(32487)

QUEST.addKillId(27340)