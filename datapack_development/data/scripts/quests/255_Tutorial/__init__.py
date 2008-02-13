import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
 
qn = "255_Tutorial"
 
# table for Quest Timer ( Ex == -2 ) [voice, html]
QTEXMTWO = {
    0  : ["tutorial_voice_001a","tutorial_human_fighter001.htm"],
    10 : ["tutorial_voice_001b","tutorial_human_mage001.htm"],
    18 : ["tutorial_voice_001c","tutorial_elven_fighter001.htm"],
    25 : ["tutorial_voice_001d","tutorial_elven_mage001.htm"],
    31 : ["tutorial_voice_001e","tutorial_delf_fighter001.htm"],
    38 : ["tutorial_voice_001f","tutorial_delf_mage001.htm"],
    44 : ["tutorial_voice_001g","tutorial_orc_fighter001.htm"],
    49 : ["tutorial_voice_001h","tutorial_orc_mage001.htm"],
    53 : ["tutorial_voice_001i","tutorial_dwarven_fighter001.htm"],
    123: ["tutorial_voice_001k","tutorial_kamael_male001.htm"],
    124: ["tutorial_voice_001j","tutorial_kamael_female001.htm"]
    }
# table for Client Event Enable (8) [html, x, y, z]
CEEa = {
    0  : ["tutorial_human_fighter007.htm",-71424,258336,-3109],
    10 : ["tutorial_human_mage007.htm",-91036,248044,-3568],
    18 : ["tutorial_elf007.htm",46112,41200,-3504],
    25 : ["tutorial_elf007.htm",46112,41200,-3504],
    31 : ["tutorial_delf007.htm",28384,11056,-4233],
    38 : ["tutorial_delf007.htm",28384,11056,-4233],
    44 : ["tutorial_orc007.htm",-56736,-113680,-672],
    49 : ["tutorial_orc007.htm",-56736,-113680,-672],
    53 : ["tutorial_dwarven_fighter007.htm",108567,-173994,-406],
    123: ["tutorial_kamael007.htm",-125872,38016,1251],
    124: ["tutorial_kamael007.htm",-125872,38016,1251]
    }
class Quest (JQuest) :
 
    def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)
 
    def onAdvEvent(self,event,npc,player):
        st = player.getQuestState(qn)
        string = event[0:2]
        htmltext = ""
        # USER CONNECTED #
 
        if string == "UC" :
            playerLevel = player.getLevel()
            if playerLevel < 6 and st.getInt("onlyone") == 0 :
                uc = st.getInt("ucMemo")
                if uc == 0 :
                    st.set("ucMemo","0")
                    st.startQuestTimer("QT",10000)
                    st.set("ucMemo","0")
                    st.set("Ex","-2")
                elif uc == 1 :
                    st.showQuestionMark(1)
                    st.playTutorialVoice("tutorial_voice_006")
                    st.playSound("ItemSound.quest_tutorial")
                elif uc == 2 :
                    if st.getInt("Ex") == 2 :
                        st.showQuestionMark(3)
                        st.playSound("ItemSound.quest_tutorial")
                    if st.getQuestItemsCount(6353) == 1 :
                        st.showQuestionMark(5)
                        st.playSound("ItemSound.quest_tutorial")
                elif uc == 3 :
                    st.showQuestionMark(12)
                    st.playSound("ItemSound.quest_tutorial")
                    st.onTutorialClientEvent(0)
                else :
                    return
 
 
        # QUEST TIMER #
 
        elif string == "QT" :
            Ex = st.getInt("Ex")
            if Ex == -2 :
                classId = int(st.getPlayer().getClassId().getId())
                voice, htmltext = QTEXMTWO[classId]
                st.playTutorialVoice(voice)
                if st.getQuestItemsCount(5588) == 0 :
                    st.giveItems(5588,1)
                st.startQuestTimer("QT",30000)
                st.set("Ex","-3")
            elif Ex == -3 :
                st.playTutorialVoice("tutorial_voice_002")
                st.set("Ex","0")
            elif Ex == -4 :
                st.playTutorialVoice("tutorial_voice_008")
                st.set("Ex","-5")
 
        # TUTORIAL CLOSE [N] #
 
        elif string == "TE" :
            event_id = int(event[2:])
            if event_id == 0 :
                st.closeTutorialHtml()
            elif event_id == 1 :
                st.closeTutorialHtml()
                st.playTutorialVoice("tutorial_voice_006")
                st.showQuestionMark(1)
                st.playSound("ItemSound.quest_tutorial")
                st.startQuestTimer("QT",30000)
                st.set("Ex","-4")
            elif event_id == 2 :
                st.playTutorialVoice("tutorial_voice_003")
                htmltext = "tutorial_02.htm"
                st.onTutorialClientEvent(1)
                st.set("Ex","-5")
            elif event_id == 3 :
                htmltext = "tutorial_03.htm"
                st.onTutorialClientEvent(2)
            elif event_id == 5 :
                htmltext = "tutorial_05.htm"
                st.onTutorialClientEvent(8)
            elif event_id == 7 :
                htmltext = "tutorial_100.htm"
                st.onTutorialClientEvent(0)
            elif event_id == 8 :
                htmltext = "tutorial_101.htm"
                st.onTutorialClientEvent(0)
            elif event_id == 10 :
                htmltext = "tutorial_103.htm"
                st.onTutorialClientEvent(0)
            elif event_id == 12 :
                st.closeTutorialHtml()
 
        # CLIENT EVENT ENABLE [N] #
 
        elif string == "CE" :
            event_id = int(event[2:])
            playerLevel = player.getLevel()
            if event_id == 1 :
                if playerLevel < 6 :
                    st.playTutorialVoice("tutorial_voice_004")
                    htmltext = "tutorial_03.htm"
                    st.playSound("ItemSound.quest_tutorial")
                    st.onTutorialClientEvent(2)
            elif event_id == 2 :
                if playerLevel < 6 :
                    st.playTutorialVoice("tutorial_voice_005")
                    htmltext = "tutorial_05.htm"
                    st.playSound("ItemSound.quest_tutorial")
                    st.onTutorialClientEvent(8)
            elif event_id == 8 :
                if playerLevel < 6 :
                    classId = int(st.getPlayer().getClassId().getId())
                    htmltext, x, y, z = CEEa[classId]
                    st.playSound("ItemSound.quest_tutorial")
                    st.addRadar(x,y,z)
                    st.playTutorialVoice("tutorial_voice_007")
                    st.set("ucMemo","1")
                    st.set("Ex","-5")
            elif event_id == 2144337408 :
                if playerLevel < 6 and st.getInt("Die") == 0:
                    st.playTutorialVoice("tutorial_voice_016")
                    st.playSound("ItemSound.quest_tutorial")
                    st.set("Die","1")
                    st.showQuestionMark(8)
            elif event_id == 40 :
                if playerLevel == 5 :
                   if st.getInt("lvl") < 5:
                     if player.getClassId().isMage() :
                      #st.playTutorialVoice("tutorial_voice_???")
                      st.showQuestionMark(30)
                     else :
                      #st.playTutorialVoice("tutorial_voice_???")
                      st.showQuestionMark(31)
                     st.playSound("ItemSound.quest_tutorial")
                     st.set("lvl","5")
                elif playerLevel == 7 and player.getClassId().isMage() :
                   if st.getInt("lvl") < 7:
                      #st.playTutorialVoice("tutorial_voice_???")
                      st.playSound("ItemSound.quest_tutorial")
                      st.set("lvl","7")
                      st.showQuestionMark(32)
                elif playerLevel == 15 :
                   if st.getInt("lvl") < 15:
                      #st.playTutorialVoice("tutorial_voice_???")
                      st.playSound("ItemSound.quest_tutorial")
                      st.set("lvl","15")
                      st.showQuestionMark(33)
            elif event_id == 45 :
                if playerLevel < 6 :
                   if st.getInt("HP") == 0:
                    st.playTutorialVoice("tutorial_voice_017")
                    st.playSound("ItemSound.quest_tutorial")
                    st.set("HP","1")
                    st.showQuestionMark(10)
                   st.onTutorialClientEvent(2144337408)
            elif event_id == 57 :
                if playerLevel < 6 and st.getInt("Adena") == 0:
                    st.playTutorialVoice("tutorial_voice_012")
                    st.playSound("ItemSound.quest_tutorial")
                    st.set("Adena","1")
                    st.showQuestionMark(23)
            elif event_id == 6353 :
                if playerLevel < 6 and st.getInt("Gemstone") == 0:
                    st.playTutorialVoice("tutorial_voice_013")
                    st.playSound("ItemSound.quest_tutorial")
                    st.set("Gemstone","1")
                    st.showQuestionMark(5)

 
        # QUESTION MARK CLICKED [N] #
 
        elif string == "QM" :
            classId = int(st.getPlayer().getClassId().getId())
            MarkId = int(event[2:])
            if MarkId == 1 :
                st.playTutorialVoice("tutorial_voice_007")
                st.set("Ex","-5")
                classId = int(st.getPlayer().getClassId().getId())
                htmltext, x, y, z = CEEa[classId]
                st.addRadar(x,y,z)
            elif MarkId == 3 :
                htmltext = "tutorial_09.htm"
            elif MarkId == 5 :
                classId = int(st.getPlayer().getClassId().getId())
                htmltext, x, y, z = CEEa[classId]
                st.addRadar(x,y,z)
                htmltext = "tutorial_11.htm"
            elif MarkId == 7 :
                htmltext = "tutorial_15.htm"
                st.set("ucMemo","3")
            elif MarkId == 8 :
                htmltext = "tutorial_21.htm"
            elif MarkId == 10 :
                htmltext = "tutorial_22.htm"
            elif MarkId == 12 :
                htmltext = "tutorial_15.htm"
                st.set("ucMemo","4")
            elif MarkId == 23 :
                htmltext = "tutorial_23.htm"
            elif MarkId == 26 :
                if st.getPlayer().getClassId().isMage() and classId != 49 :
                    htmltext = "tutorial_newbie004b.htm"
                else :
                    htmltext = "tutorial_newbie004a.htm"
            elif MarkId == 30 :
                htmltext = "tutorial_mage017.htm"
            elif MarkId == 31 :
                htmltext = "tutorial_fighter017.htm"
            elif MarkId == 32 :
                htmltext = "tutorial_mage020.htm"
            elif MarkId == 33 :
                htmltext = "tutorial_27.htm"
        if htmltext == "": return
        st.showTutorialHTML(htmltext)
        return
 
QUEST = Quest(255,qn,"Tutorial")
