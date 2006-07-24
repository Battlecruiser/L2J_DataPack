# Made by mtrix
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ORB = 4364
ECTOPLASM = 4365
ADENA = 57
CHANCE = 65
RANDOM_REWARDS=[[951,1],   #Enchant Weapon C
                [955,1],   #Enchant Weapon D
                [2511,550],#SpiritShot: Grade C
                [736,1],   #SoE
               ]


class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
     htmltext = event
     random1 = st.getRandom(3)
     random2 = st.getRandom(2)
     orbs = st.getQuestItemsCount(ORB)
     if event == "7834-02.htm" :
         st.setState(STARTED)
         st.set("cond","1")
         st.playSound("ItemSound.quest_accept")
     elif event == "7834-05.htm" :
         if orbs :
            st.giveItems(ADENA,orbs*125)
            st.takeItems(ORB,-1)
         else :
            htmltext = "You don't have enough items"
     elif event == "7934-02.htm" :
         if orbs < 10 :
             htmltext = "<html><head><body>You dont have enough orbs! Come back when you have some!</body></html>"
     elif event == "7934-03.htm" :
         if orbs>=10 :
             st.takeItems(ORB,10)
             st.set("playing","1")
         else :
             htmltext = "<html><head><body>You dont have enough orbs! Come back when you have some!</body></html>"
     elif event == "7934-04.htm" :
         if st.getInt("playing"):
           if random1==0 :
             htmltext = "7934-05.htm"
             st.giveItems(ORB,10)
           elif random1==1 :
             htmltext = "7934-06.htm"
           elif random1==2 :
             st.giveItems(ORB,20)
           st.unset("playing")
         else:
           htmltext="Player is cheating"
           st.takeItems(ORB,-1)
     elif event == "7934-05.htm" :
         if st.getInt("playing"):
           if random1==0 :
             htmltext = "7934-04.htm"
             st.giveItems(ORB,20)
           elif random1==1 :
             st.giveItems(ORB,10)
           elif random1==2 :
             htmltext = "7934-06.htm"
           st.unset("playing")
         else:
           htmltext="Player is cheating"
           st.takeItems(ORB,-1)
     elif event == "7934-06.htm" :
         if st.getInt("playing"):
           if random1==1 :
             htmltext = "7934-04.htm"
             st.giveItems(ORB,20)
           elif random1==2 :
             htmltext = "7934-05.htm"
             st.giveItems(ORB,10)
           st.unset("playing")
         else:
           htmltext="Player is cheating"
           st.takeItems(ORB,-1)
     elif event == "7935-02.htm" :
         if orbs < 10 :
             htmltext = "<html><head><body>You dont have enough orbs! Come back when you have some!</body></html>"
     elif event == "7935-03.htm" :
         if orbs>=10 :
             st.takeItems(ORB,10)
         else :
             htmltext = "<html><head><body>You dont have enough orbs! Come back when you have some!</body></html>"
     elif event == "7935-05.htm" :
         if random2==0 :
             row = st.getInt("row")
             row += 1
             st.giveItems(ORB,row*20)
             htmltext = "7935-04.htm"
         else :
             row = 0
         st.set("row",str(row))
     elif event == "7834-06.htm" :
         st.playSound("ItemSound.quest_finish")
         st.exitQuest(1)
     return htmltext

 def onTalk (Self,npc,st):
     npcId = npc.getNpcId()
     htmltext = "<html><head><body>I have nothing to say you</body></html>"
     id = st.getState()
     level = st.getPlayer().getLevel()
     cond = st.getInt("cond")
     if npcId==7834 :
         if id == CREATED :
             if st.getPlayer().getClassId().getId() in [ 0x11,0xc,0xd,0xe,0x10,0x1a,0x1b,0x1c,0x1e,0x28,0x29,0x2b,0x5e,0x5f,0x60,0x61,0x62,0x67,0x68,0x69,0x6e,0x6f,0x70] and level >=40:
                 htmltext = "7834-01.htm"
             else:
                 htmltext = "<html><head><body>This quest can only be taken by characters level 40 and higher!<br> And it is only for Mystics except Orcs</body></html>"
         elif cond==1 :
             if st.getQuestItemsCount(ORB) :
               htmltext = "7834-04.htm"
             else :
               htmltext = "7834-03.htm"
     elif npcId==7835 :
         if st.getQuestItemsCount(ECTOPLASM) :
             st.takeItems(ECTOPLASM,1)
             item=RANDOM_REWARDS[st.getRandom(len(RANDOM_REWARDS))]
             st.giveItems(item[0],item[1])
     elif npcId==7934 :
         htmltext = "7934-01.htm"
     elif npcId==7935 :
         htmltext = "7935-01.htm"
     return htmltext

 def onKill (self,npc,st):
     npcId = npc.getNpcId()
     cond = st.getInt("cond")
     if st.getRandom(100) < CHANCE :
         st.giveItems(ORB,1)
         st.playSound("ItemSound.quest_itemget")
     return

QUEST       = Quest(343,"343_UnderTheShadowOfTheIvoryTower","Under The Shadow Of The Ivory Tower")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7834)

CREATED.addTalkId(7834)
CREATED.addTalkId(7835)
STARTED.addTalkId(7834)
STARTED.addTalkId(7835)
STARTED.addTalkId(7934)
STARTED.addTalkId(7935)

for i in range(563,567) :
    STARTED.addKillId(i)

STARTED.addQuestDrop(7834,ORB,1)

print "importing quests: 343: Under The Shadow Of The Ivory Tower"
