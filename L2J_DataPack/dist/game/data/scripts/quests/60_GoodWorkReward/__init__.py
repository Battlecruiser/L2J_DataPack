# Made by Kerberos v1.0 on 2008/07/31
# this script is part of the Official L2J Datapack Project.
# Visit http://www.l2jdp.com/forum/ for more details.

import sys
from com.l2jserver.gameserver.ai import CtrlIntention
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest
from com.l2jserver.gameserver.network.serverpackets import NpcSay

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
56:"<a action=\"bypass -h Quest 60_GoodWorkReward WS\">Warsmith.</a>"
}

CLASSES = {
"AW":[36,[2673,3172,2809]],
"BD":[34,[2627,3172,2762]],
"BH":[55,[2809,3119,3238]],
"BS":[16,[2721,2734,2820]],
"DA":[6,[2633,2734,3307]],
"DT":[46,[2627,3203,3276]],
"EE":[30,[2721,3140,2820]],
"ES":[28,[2674,3140,3336]],
"GL":[2,[2627,2734,2762]],
"HK":[9,[2673,2734,3293]],
"NM":[13,[2674,2734,3307]],
"OL":[51,[2721,3203,3390]],
"PA":[5,[2633,2734,2820]],
"PP":[17,[2721,2734,2821]],
"PR":[37,[2673,3172,3293]],
"PS":[41,[2674,3172,3336]],
"PW":[23,[2673,3140,2809]],
"SC":[12,[2674,2734,2840]],
"SE":[43,[2721,3172,2821]],
"SH":[40,[2674,3172,2840]],
"SK":[33,[2633,3172,3307]],
"SP":[27,[2674,3140,2840]],
"SR":[24,[2673,3140,3293]],
"SS":[21,[2627,3140,2762]],
"TH":[8,[2673,2734,2809]],
"TK":[20,[2633,3140,2820]],
"TR":[48,[2627,3203,2762]],
"WA":[14,[2674,2734,3336]],
"WC":[52,[2721,3203,2879]],
"WL":[3,[2627,2734,3276]],
"WS":[57,[2867,3119,3238]]
}

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [10867,10868]
     self.isNpcSpawned = 0

 def onAdvEvent (self,event,npc,player) :
    if event == "npc_cleanup" :
      self.isNpcSpawned = 0
      return
    st = player.getQuestState(qn)
    if not st: return
    htmltext = event
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
      self.startQuestTimer("npc_cleanup",1800000,None, None)
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
         htmltext = "30081-07.htm"
    elif event == "31092-05.htm" :
      st.exitQuest(False)
      st.playSound("ItemSound.quest_finish")
      if player.getClassId().level() == 1 :
         text = BYPASS[player.getClassId().getId()]
         htmltext = "<html><body>Black Marketeer of Mammon:<br>Forget about the money!<br>I will help you complete the class transfer, which is far more valuable! Which class would you like to be? Choose one.<br>"+text+"</body></html>"
      else :
         htmltext = "31092-06.htm"
    elif event == "31092-06.htm" :
      text = BYPASS[player.getClassId().getId()]
      htmltext = "<html><body>Black Marketeer of Mammon:<br>If you are finished thinking, select one. Which class would you like to be?<br>"+text+"</body></html>"
    elif event == "31092-07.htm" :
      st.giveAdena(3000000,True)
      st.set("onlyone","1")
    elif event in CLASSES.keys():
         newclass,req_item=CLASSES[event]
         adena = 0
         for i in req_item :
            if not st.getQuestItemsCount(i):
               st.giveItems(i,1)
            else :
               adena = adena + 1
         if adena == 3 :
            return "31092-06.htm"
         if adena > 0 :
            st.giveAdena(adena*1000000,True)
         htmltext = "31092-05.htm"
         st.set("onlyone","1")
    return htmltext

 def onTalk (self,npc,player):
   htmltext = Quest.getNoQuestMsg(player)
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   cond = st.getInt("cond")
   id = st.getState()
   if id == State.COMPLETED :
     if npcId == 31435 :
        htmltext = Quest.getAlreadyCompletedMsg(player)
     elif npcId == 31092 :
        if player.getClassId().level() == 1 and not st.getInt("onlyone"):
           htmltext = "31092-04.htm"
   if id == State.CREATED and npcId == 31435 :
     if player.getLevel() < 39 or player.getClassId().level() != 1 or player.getRace().ordinal() == 5:
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
   self.cancelQuestTimer("npc_cleanup", None, None)
   self.isNpcSpawned = 0
   st = player.getQuestState(qn)
   if not st : return
   if st.getState() != State.STARTED : return
   npcId = npc.getNpcId()
   cond = st.getInt("cond")
   if npcId == 27340 and cond == 1:
     string = "You are strong. This was a mistake."
     if self.getRandom(1):
       string = "You have good luck. I shall return."
     npc.broadcastPacket(NpcSay(npc.getObjectId(),0,npc.getNpcId(),string))
     st.giveItems(10867,1)
     st.set("cond","2")
     st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(60,qn,"Good Work's Reward")

QUEST.addStartNpc(31092)
QUEST.addStartNpc(31435)
QUEST.addTalkId(30081)
QUEST.addTalkId(31092)
QUEST.addTalkId(31435)
QUEST.addTalkId(32487)

QUEST.addKillId(27340)