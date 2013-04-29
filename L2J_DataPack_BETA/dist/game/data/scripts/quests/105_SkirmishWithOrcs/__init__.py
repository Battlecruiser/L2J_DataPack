# Made by Mr. Have fun! Version 0.3 updated by Sh1ning for www.l2jdp.com 
import sys
from com.l2jserver import Config
from com.l2jserver.gameserver.model.quest import State 
from com.l2jserver.gameserver.model.quest import QuestState 
from com.l2jserver.gameserver.model.quest import Quest as JQuest 
from com.l2jserver.gameserver.network.serverpackets      import SocialAction

qn = "105_SkirmishWithOrcs" 

KENDNELLS_ORDER1 = 1836 
KENDNELLS_ORDER2 = 1837 
KENDNELLS_ORDER3 = 1838 
KENDNELLS_ORDER4 = 1839 
KENDNELLS_ORDER5 = 1840 
KENDNELLS_ORDER6 = 1841 
KENDNELLS_ORDER7 = 1842 
KENDNELLS_ORDER8 = 1843 
KABOO_CHIEF_TORC1 = 1844 
KABOO_CHIEF_TORC2 = 1845 
RED_SUNSET_SWORD = 981 
RED_SUNSET_STAFF = 754

#Newbie/one time rewards section
#Any quest should rely on a unique bit, but
#it could be shared among quest that were mutually
#exclusive or race restricted.
#Bit #1 isn't used for backwards compatibility.
NEWBIE_REWARD = 2
SPIRITSHOT_NO_GRADE_FOR_BEGINNERS = 5790 
SPIRITSHOT_NO_GRADE = 2509 
SOULSHOT_NO_GRADE_FOR_BEGINNERS = 5789
SOULSHOT_NO_GRADE = 1835



class Quest (JQuest) : 

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [KENDNELLS_ORDER1, KENDNELLS_ORDER2, KENDNELLS_ORDER3, KENDNELLS_ORDER4, KENDNELLS_ORDER5, KENDNELLS_ORDER6, KENDNELLS_ORDER7, KENDNELLS_ORDER8]

 def onAdvEvent (self,event,npc, player) :
    htmltext = event
    st = player.getQuestState(qn)
    if not st : return
    if event == "1" : 
      st.set("id","0") 
      st.set("cond","1") 
      st.setState(State.STARTED) 
      st.playSound("ItemSound.quest_accept") 
      htmltext = "30218-03.htm" 
      if st.getQuestItemsCount(KENDNELLS_ORDER1)+st.getQuestItemsCount(KENDNELLS_ORDER2)+st.getQuestItemsCount(KENDNELLS_ORDER3)+st.getQuestItemsCount(KENDNELLS_ORDER4) == 0 : 
        n = self.getRandom(100) 
        if n < 25 : 
          st.giveItems(KENDNELLS_ORDER1,1) 
        elif n < 50 : 
          st.giveItems(KENDNELLS_ORDER2,1) 
        elif n < 75 : 
          st.giveItems(KENDNELLS_ORDER3,1) 
        else: 
          st.giveItems(KENDNELLS_ORDER4,1) 
    return htmltext 


 def onTalk (self,npc,player): 

   npcId = npc.getNpcId() 
   htmltext = Quest.getNoQuestMsg(player) 
   st = player.getQuestState(qn) 
   if not st : return htmltext 
    
   id = st.getState() 
   if id == State.COMPLETED :
      htmltext = Quest.getAlreadyCompletedMsg(player)
   elif npcId == 30218 and id == State.CREATED : 
      if player.getLevel() >= 10 and player.getRace().ordinal() == 1 : 
        htmltext = "30218-02.htm" 
        return htmltext 
      elif player.getRace().ordinal() != 1 : 
        htmltext = "30218-00.htm" 
        st.exitQuest(1) 
      else: 
        htmltext = "30218-10.htm" 
        st.exitQuest(1) 
   elif npcId == 30218 and st.getInt("cond") : 
      if st.getQuestItemsCount(KABOO_CHIEF_TORC1) : 
        htmltext = "30218-06.htm" 
        if st.getQuestItemsCount(KENDNELLS_ORDER1) : 
          st.takeItems(KENDNELLS_ORDER1,1) 
        if st.getQuestItemsCount(KENDNELLS_ORDER2) : 
          st.takeItems(KENDNELLS_ORDER2,1) 
        if st.getQuestItemsCount(KENDNELLS_ORDER3) : 
          st.takeItems(KENDNELLS_ORDER3,1) 
        if st.getQuestItemsCount(KENDNELLS_ORDER4) : 
          st.takeItems(KENDNELLS_ORDER4,1) 
        st.takeItems(KABOO_CHIEF_TORC1,1) 
        n = self.getRandom(100) 
        if n < 25 : 
          st.giveItems(KENDNELLS_ORDER5,1) 
        elif n < 50 : 
          st.giveItems(KENDNELLS_ORDER6,1) 
        elif n < 75 : 
          st.giveItems(KENDNELLS_ORDER7,1) 
        else: 
          st.giveItems(KENDNELLS_ORDER8,1) 
        st.set("cond","3") 
      elif st.getQuestItemsCount(KENDNELLS_ORDER1) or st.getQuestItemsCount(KENDNELLS_ORDER2) or st.getQuestItemsCount(KENDNELLS_ORDER3) or st.getQuestItemsCount(KENDNELLS_ORDER4) : 
        htmltext = "30218-05.htm" 
      elif st.getQuestItemsCount(KABOO_CHIEF_TORC2) : 
        if st.getInt("id") != 105 :
            st.set("id","105") 
            htmltext = "30218-08.htm" 
            if st.getQuestItemsCount(KENDNELLS_ORDER5) :
                st.takeItems(KENDNELLS_ORDER5,1)
            if st.getQuestItemsCount(KENDNELLS_ORDER6) : 
                st.takeItems(KENDNELLS_ORDER6,1) 
            if st.getQuestItemsCount(KENDNELLS_ORDER7) : 
                st.takeItems(KENDNELLS_ORDER7,1) 
            if st.getQuestItemsCount(KENDNELLS_ORDER8) : 
                st.takeItems(KENDNELLS_ORDER8,1) 
            st.takeItems(KABOO_CHIEF_TORC2,1)
            newbie = player.getNewbie()
            mage = player.getClassId().isMage()
            st.giveItems(57,17599)
            if mage :
               st.giveItems(RED_SUNSET_STAFF,1)
               st.giveItems(SPIRITSHOT_NO_GRADE,500)
            else : 
               st.giveItems(RED_SUNSET_SWORD,1)
               st.giveItems(SOULSHOT_NO_GRADE,1000)
            if newbie | NEWBIE_REWARD != newbie :
               player.setNewbie(newbie|NEWBIE_REWARD)
               if mage :
                  st.playTutorialVoice("tutorial_voice_027")
                  st.giveItems(SPIRITSHOT_NO_GRADE_FOR_BEGINNERS,3000)
               else :
                  st.playTutorialVoice("tutorial_voice_026")
                  st.giveItems(SOULSHOT_NO_GRADE_FOR_BEGINNERS,7000)
               st.playSound("ItemSound.quest_tutorial")
            st.rewardItems(1060,100)     # Lesser Healing Potions 
            for item in range(4412,4417) : 
                st.rewardItems(item,10)   # Echo crystals 
            st.addExpAndSp(41478,3555)
            player.sendPacket(SocialAction(player.getObjectId(),3))
            st.exitQuest(False) 
            st.playSound("ItemSound.quest_finish") 
            st.unset("cond") 
      elif st.getQuestItemsCount(KENDNELLS_ORDER5) or st.getQuestItemsCount(KENDNELLS_ORDER6) or st.getQuestItemsCount(KENDNELLS_ORDER7) or st.getQuestItemsCount(KENDNELLS_ORDER8) : 
        htmltext = "30218-07.htm" 
   return htmltext 

 def onKill(self,npc,player,isPet): 
   st = player.getQuestState(qn) 
   if not st : return 
   if st.getState() != State.STARTED : return 
   npcId = npc.getNpcId() 
   if npcId == 27059 : 
    st.set("id","0") 
    if st.getInt("cond") == 1 : 
     if st.getQuestItemsCount(KENDNELLS_ORDER1) and st.getQuestItemsCount(KABOO_CHIEF_TORC1) == 0 : 
      st.giveItems(KABOO_CHIEF_TORC1,1) 
      st.playSound("ItemSound.quest_middle") 
      st.set("cond","2") 
   elif npcId == 27060 : 
    st.set("id","0") 
    if st.getInt("cond") == 1 : 
     if st.getQuestItemsCount(KENDNELLS_ORDER2) and st.getQuestItemsCount(KABOO_CHIEF_TORC1) == 0 : 
      st.giveItems(KABOO_CHIEF_TORC1,1) 
      st.playSound("ItemSound.quest_middle") 
      st.set("cond","2") 
   elif npcId == 27061 : 
    st.set("id","0") 
    if st.getInt("cond") == 1 : 
     if st.getQuestItemsCount(KENDNELLS_ORDER3) and st.getQuestItemsCount(KABOO_CHIEF_TORC1) == 0 : 
      st.giveItems(KABOO_CHIEF_TORC1,1) 
      st.playSound("ItemSound.quest_middle") 
      st.set("cond","2") 
   elif npcId == 27062 : 
    st.set("id","0") 
    if st.getInt("cond") == 1 : 
     if st.getQuestItemsCount(KENDNELLS_ORDER4) and st.getQuestItemsCount(KABOO_CHIEF_TORC1) == 0 : 
      st.giveItems(KABOO_CHIEF_TORC1,1) 
      st.playSound("ItemSound.quest_middle") 
      st.set("cond","2") 
   elif npcId == 27064 : 
    st.set("id","0") 
    if st.getInt("cond") == 3 : 
     if st.getQuestItemsCount(KENDNELLS_ORDER5) and st.getQuestItemsCount(KABOO_CHIEF_TORC2) == 0 : 
      st.giveItems(KABOO_CHIEF_TORC2,1) 
      st.playSound("ItemSound.quest_middle") 
      st.set("cond","4") 
   elif npcId == 27065 : 
    st.set("id","0") 
    if st.getInt("cond") == 3 : 
     if st.getQuestItemsCount(KENDNELLS_ORDER6) and st.getQuestItemsCount(KABOO_CHIEF_TORC2) == 0 : 
      st.giveItems(KABOO_CHIEF_TORC2,1) 
      st.playSound("ItemSound.quest_middle") 
      st.set("cond","4") 
   elif npcId == 27067 : 
    st.set("id","0") 
    if st.getInt("cond") == 3 : 
     if st.getQuestItemsCount(KENDNELLS_ORDER7) and st.getQuestItemsCount(KABOO_CHIEF_TORC2) == 0 : 
      st.giveItems(KABOO_CHIEF_TORC2,1) 
      st.playSound("ItemSound.quest_middle") 
      st.set("cond","4") 
   elif npcId == 27068 : 
    st.set("id","0") 
    if st.getInt("cond") == 3 : 
     if st.getQuestItemsCount(KENDNELLS_ORDER8) and st.getQuestItemsCount(KABOO_CHIEF_TORC2) == 0 : 
      st.giveItems(KABOO_CHIEF_TORC2,1) 
      st.playSound("ItemSound.quest_middle") 
      st.set("cond","4") 
   return 

QUEST       = Quest(105,qn,"Skirmish with the Orcs") 

QUEST.addStartNpc(30218) 

QUEST.addTalkId(30218) 

QUEST.addKillId(27059) 
QUEST.addKillId(27060) 
QUEST.addKillId(27061) 
QUEST.addKillId(27062) 
QUEST.addKillId(27064) 
QUEST.addKillId(27065) 
QUEST.addKillId(27067) 
QUEST.addKillId(27068) 
