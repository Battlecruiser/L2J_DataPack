print "importing blacksmith data: 1004_enhance"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "0":
        htmltext = "Trade has been cancelled."
    if event == "1":
        htmltext = "60.htm"

    if event == "1" and st.getQuestItemsCount(281) == 1:
        htmltext = "12.htm"
	
    if event == "1" and st.getQuestItemsCount(285) == 1:
        htmltext = "13.htm"

    if event == "1" and st.getQuestItemsCount(283) == 1:
        htmltext = "15.htm"

    if event == "1" and st.getQuestItemsCount(286) == 1:
        htmltext = "16.htm"

    if event == "1" and st.getQuestItemsCount(284) == 1:
        htmltext = "17.htm"

    if event == "1" and st.getQuestItemsCount(287) == 1:
        htmltext = "18.htm"

    if event == "1" and st.getQuestItemsCount(72) == 1:
        htmltext = "19.htm"

    if event == "1" and st.getQuestItemsCount(74) == 1:
        htmltext = "20.htm"

    if event == "1" and st.getQuestItemsCount(131) == 1:
        htmltext = "21.htm"

    if event == "1" and st.getQuestItemsCount(133) == 1:
        htmltext = "22.htm"

    if event == "1" and st.getQuestItemsCount(73) == 1:
        htmltext = "23.htm"

    if event == "1" and st.getQuestItemsCount(76) == 1:
        htmltext = "24.htm"

    if event == "1" and st.getQuestItemsCount(77) == 1:
        htmltext = "25.htm"

    if event == "1" and st.getQuestItemsCount(134) == 1:
        htmltext = "26.htm"

    if event == "1" and st.getQuestItemsCount(132) == 1:
        htmltext = "27.htm"

    if event == "1" and st.getQuestItemsCount(79) == 1:
        htmltext = "28.htm"

    if event == "1" and st.getQuestItemsCount(142) == 1:
        htmltext = "29.htm"

    if event == "1" and st.getQuestItemsCount(78) == 1:
        htmltext = "30.htm"

    if event == "1" and st.getQuestItemsCount(162) == 1:
        htmltext = "31.htm"

    if event == "1" and st.getQuestItemsCount(2503) == 1:
        htmltext = "32.htm"

    if event == "1" and st.getQuestItemsCount(192) == 1:
        htmltext = "33.htm"

    if event == "1" and st.getQuestItemsCount(195) == 1:
        htmltext = "34.htm"

    if event == "1" and st.getQuestItemsCount(197) == 1:
        htmltext = "35.htm"

    if event == "1" and st.getQuestItemsCount(200) == 1:
        htmltext = "36.htm"

    if event == "1" and st.getQuestItemsCount(203) == 1:
        htmltext = "37.htm"

    if event == "1" and st.getQuestItemsCount(205) == 1:
        htmltext = "38.htm"

    if event == "1" and st.getQuestItemsCount(206) == 1:
        htmltext = "39.htm"

    if event == "1" and st.getQuestItemsCount(204) == 1:
        htmltext = "40.htm"

    if event == "1" and st.getQuestItemsCount(92) == 1:
        htmltext = "41.htm"

    if event == "1" and st.getQuestItemsCount(210) == 1:
        htmltext = "42.htm"

    if event == "1" and st.getQuestItemsCount(91) == 1:
        htmltext = "43.htm"

    if event == "1" and st.getQuestItemsCount(175) == 1:
        htmltext = "44.htm"

    if event == "1" and st.getQuestItemsCount(171) == 1:
        htmltext = "45.htm"

    if event == "1" and st.getQuestItemsCount(228) == 1:
        htmltext = "46.htm"

    if event == "1" and st.getQuestItemsCount(233) == 1:
        htmltext = "47.htm"

    if event == "1" and st.getQuestItemsCount(231) == 1:
        htmltext = "48.htm"

    if event == "1" and st.getQuestItemsCount(229) == 1:
        htmltext = "49.htm"

    if event == "1" and st.getQuestItemsCount(234) == 1:
        htmltext = "50.htm"

    if event == "1" and st.getQuestItemsCount(301) == 1:
        htmltext = "51.htm"

    if event == "1" and st.getQuestItemsCount(303) == 1:
        htmltext = "52.htm"

    if event == "1" and st.getQuestItemsCount(299) == 1:
        htmltext = "53.htm"

    if event == "1" and st.getQuestItemsCount(300) == 1:
        htmltext = "54.htm"

    if event == "1" and st.getQuestItemsCount(97) == 1:
        htmltext = "55.htm"

    if event == "1" and st.getQuestItemsCount(265) == 1:
        htmltext = "56.htm"

    if event == "1" and st.getQuestItemsCount(266) == 1:
        htmltext = "57.htm"

    if event == "1" and st.getQuestItemsCount(267) == 1:
        htmltext = "58.htm"

    if event == "1" and st.getQuestItemsCount(268) == 1:
        htmltext = "59.htm"

    if event == "1" and st.getQuestItemsCount(145) == 1:
        htmltext = "61.htm"

    if event == "1" and st.getQuestItemsCount(84) == 1:
        htmltext = "62.htm"

    #Bows
    if event == "2":
        if st.getQuestItemsCount(281) == 1 and st.getQuestItemsCount(4634) == 1 and st.getQuestItemsCount(2131) >= 250 :
            st.takeItems(281,1)
            st.takeItems(4634,1)
            st.takeItems(2131,250)
            st.giveItems(4810,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "3":
        if st.getQuestItemsCount(281) == 1 and st.getQuestItemsCount(4645) == 1 and st.getQuestItemsCount(2131) >= 250 :
            st.takeItems(281,1)
            st.takeItems(4645,1)
            st.takeItems(2131,250)
            st.giveItems(4811,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "4":
        if st.getQuestItemsCount(281) == 1 and st.getQuestItemsCount(4656) == 1 and st.getQuestItemsCount(2131) >= 250 :
            st.takeItems(281,1)
            st.takeItems(4656,1)
            st.takeItems(2131,250)
            st.giveItems(4812,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "5":
        if st.getQuestItemsCount(285) == 1 and st.getQuestItemsCount(4635) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(285,1)
            st.takeItems(4635,1)
            st.takeItems(2131,350)
            st.giveItems(4816,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "6":
        if st.getQuestItemsCount(285) == 1 and st.getQuestItemsCount(4646) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(285,1)
            st.takeItems(4646,1)
            st.takeItems(2131,350)
            st.giveItems(4817,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "7":
        if st.getQuestItemsCount(285) == 1 and st.getQuestItemsCount(4657) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(285,1)
            st.takeItems(4657,1)
            st.takeItems(2131,350)
            st.giveItems(4818,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "8":
        if st.getQuestItemsCount(283) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(283,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4819,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "9":
        if st.getQuestItemsCount(283) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(283,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4820,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "10":
        if st.getQuestItemsCount(283) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(283,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4821,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "11":
        if st.getQuestItemsCount(286) == 1 and st.getQuestItemsCount(4637) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(286,1)
            st.takeItems(4637,1)
            st.takeItems(2131,550)
            st.giveItems(4822,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "12":
        if st.getQuestItemsCount(286) == 1 and st.getQuestItemsCount(4648) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(286,1)
            st.takeItems(4648,1)
            st.takeItems(2131,550)
            st.giveItems(4823,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "13":
        if st.getQuestItemsCount(286) == 1 and st.getQuestItemsCount(4659) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(286,1)
            st.takeItems(4659,1)
            st.takeItems(2131,550)
            st.giveItems(4824,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "14":
        if st.getQuestItemsCount(284) == 1 and st.getQuestItemsCount(4639) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(284,1)
            st.takeItems(4639,1)
            st.takeItems(2132,222)
            st.giveItems(4825,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "15":
        if st.getQuestItemsCount(284) == 1 and st.getQuestItemsCount(4650) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(284,1)
            st.takeItems(4650,1)
            st.takeItems(2132,222)
            st.giveItems(4826,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "16":
        if st.getQuestItemsCount(284) == 1 and st.getQuestItemsCount(4661) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(284,1)
            st.takeItems(4661,1)
            st.takeItems(2132,222)
            st.giveItems(4827,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "17":
        if st.getQuestItemsCount(287) == 1 and st.getQuestItemsCount(4639) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(287,1)
            st.takeItems(4639,1)
            st.takeItems(2132,339)
            st.giveItems(4828,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "18":
        if st.getQuestItemsCount(287) == 1 and st.getQuestItemsCount(4650) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(287,1)
            st.takeItems(4650,1)
            st.takeItems(2132,339)
            st.giveItems(4829,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "19":
        if st.getQuestItemsCount(287) == 1 and st.getQuestItemsCount(4661) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(287,1)
            st.takeItems(4661,1)
            st.takeItems(2132,339)
            st.giveItems(4830,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    #Swords
    if event == "20":
        if st.getQuestItemsCount(72) == 1 and st.getQuestItemsCount(4634) == 1 and st.getQuestItemsCount(2131) >= 250 :
            st.takeItems(72,1)
            st.takeItems(4634,1)
            st.takeItems(2131,250)
            st.giveItems(4681,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "21":
        if st.getQuestItemsCount(72) == 1 and st.getQuestItemsCount(4645) == 1 and st.getQuestItemsCount(2131) >= 250 :
            st.takeItems(72,1)
            st.takeItems(4645,1)
            st.takeItems(2131,250)
            st.giveItems(4682,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "22":
        if st.getQuestItemsCount(72) == 1 and st.getQuestItemsCount(4656) == 1 and st.getQuestItemsCount(2131) >= 250 :
            st.takeItems(72,1)
            st.takeItems(4656,1)
            st.takeItems(2131,250)
            st.giveItems(4683,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "23":
        if st.getQuestItemsCount(74) == 1 and st.getQuestItemsCount(4635) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(74,1)
            st.takeItems(4635,1)
            st.takeItems(2131,350)
            st.giveItems(4687,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "24":
        if st.getQuestItemsCount(74) == 1 and st.getQuestItemsCount(4646) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(74,1)
            st.takeItems(4646,1)
            st.takeItems(2131,350)
            st.giveItems(4688,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "25":
        if st.getQuestItemsCount(74) == 1 and st.getQuestItemsCount(4657) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(74,1)
            st.takeItems(4657,1)
            st.takeItems(2131,350)
            st.giveItems(4689,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "26":
        if st.getQuestItemsCount(131) == 1 and st.getQuestItemsCount(4635) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(131,1)
            st.takeItems(4635,1)
            st.takeItems(2131,350)
            st.giveItems(4690,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "27":
        if st.getQuestItemsCount(131) == 1 and st.getQuestItemsCount(4646) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(131,1)
            st.takeItems(4646,1)
            st.takeItems(2131,350)
            st.giveItems(4691,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "28":
        if st.getQuestItemsCount(131) == 1 and st.getQuestItemsCount(4657) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(131,1)
            st.takeItems(4657,1)
            st.takeItems(2131,350)
            st.giveItems(4692,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "29":
        if st.getQuestItemsCount(133) == 1 and st.getQuestItemsCount(4635) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(133,1)
            st.takeItems(4635,1)
            st.takeItems(2131,350)
            st.giveItems(4693,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "30":
        if st.getQuestItemsCount(133) == 1 and st.getQuestItemsCount(4646) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(133,1)
            st.takeItems(4646,1)
            st.takeItems(2131,350)
            st.giveItems(4694,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "31":
        if st.getQuestItemsCount(133) == 1 and st.getQuestItemsCount(4657) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(133,1)
            st.takeItems(4657,1)
            st.takeItems(2131,350)
            st.giveItems(4695,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "32":
        if st.getQuestItemsCount(73) == 1 and st.getQuestItemsCount(4635) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(73,1)
            st.takeItems(4635,1)
            st.takeItems(2131,350)
            st.giveItems(4684,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "33":
        if st.getQuestItemsCount(73) == 1 and st.getQuestItemsCount(4646) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(73,1)
            st.takeItems(4646,1)
            st.takeItems(2131,350)
            st.giveItems(4685,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "34":
        if st.getQuestItemsCount(73) == 1 and st.getQuestItemsCount(4657) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(73,1)
            st.takeItems(4657,1)
            st.takeItems(2131,350)
            st.giveItems(4686,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "35":
        if st.getQuestItemsCount(76) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(76,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4699,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "36":
        if st.getQuestItemsCount(76) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(76,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4700,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "37":
        if st.getQuestItemsCount(76) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(76,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4701,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "38":
        if st.getQuestItemsCount(77) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(77,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4702,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "39":
        if st.getQuestItemsCount(77) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(77,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4703,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "40":
        if st.getQuestItemsCount(77) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(77,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4704,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "41":
        if st.getQuestItemsCount(134) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(134,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4705,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "42":
        if st.getQuestItemsCount(134) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(134,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4706,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "43":
        if st.getQuestItemsCount(134) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(134,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4707,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "44":
        if st.getQuestItemsCount(132) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(132,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(6307,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "45":
        if st.getQuestItemsCount(132) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(132,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(6308,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "46":
        if st.getQuestItemsCount(132) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(132,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(6309,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "47":
        if st.getQuestItemsCount(79) == 1 and st.getQuestItemsCount(4639) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(79,1)
            st.takeItems(4639,1)
            st.takeItems(2132,339)
            st.giveItems(4717,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "48":
        if st.getQuestItemsCount(79) == 1 and st.getQuestItemsCount(4650) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(79,1)
            st.takeItems(4650,1)
            st.takeItems(2132,339)
            st.giveItems(4718,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "49":
        if st.getQuestItemsCount(79) == 1 and st.getQuestItemsCount(4661) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(79,1)
            st.takeItems(4661,1)
            st.takeItems(2132,339)
            st.giveItems(4719,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "50":
        if st.getQuestItemsCount(142) == 1 and st.getQuestItemsCount(4638) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(142,1)
            st.takeItems(4638,1)
            st.takeItems(2132,222)
            st.giveItems(4714,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "51":
        if st.getQuestItemsCount(142) == 1 and st.getQuestItemsCount(4649) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(142,1)
            st.takeItems(4649,1)
            st.takeItems(2132,222)
            st.giveItems(4715,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "52":
        if st.getQuestItemsCount(142) == 1 and st.getQuestItemsCount(4660) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(142,1)
            st.takeItems(4660,1)
            st.takeItems(2132,222)
            st.giveItems(4716,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "53":
        if st.getQuestItemsCount(78) == 1 and st.getQuestItemsCount(4638) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(78,1)
            st.takeItems(4638,1)
            st.takeItems(2132,222)
            st.giveItems(4723,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "54":
        if st.getQuestItemsCount(78) == 1 and st.getQuestItemsCount(4649) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(78,1)
            st.takeItems(4649,1)
            st.takeItems(2132,222)
            st.giveItems(4724,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "55":
        if st.getQuestItemsCount(78) == 1 and st.getQuestItemsCount(4660) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(78,1)
            st.takeItems(4660,1)
            st.takeItems(2132,222)
            st.giveItems(4725,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    #Blunts
    if event == "56":
        if st.getQuestItemsCount(162) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(162,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4741,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "57":
        if st.getQuestItemsCount(162) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(162,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4742,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "58":
        if st.getQuestItemsCount(162) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(162,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4743,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "59":
        if st.getQuestItemsCount(2503) == 1 and st.getQuestItemsCount(4637) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(2503,1)
            st.takeItems(4637,1)
            st.takeItems(2131,550)
            st.giveItems(4744,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "60":
        if st.getQuestItemsCount(2503) == 1 and st.getQuestItemsCount(4648) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(2503,1)
            st.takeItems(4648,1)
            st.takeItems(2131,550)
            st.giveItems(4745,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "61":
        if st.getQuestItemsCount(2503) == 1 and st.getQuestItemsCount(4659) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(2503,1)
            st.takeItems(4659,1)
            st.takeItems(2131,550)
            st.giveItems(4746,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "62":
        if st.getQuestItemsCount(192) == 1 and st.getQuestItemsCount(4634) == 1 and st.getQuestItemsCount(2131) >= 250 :
            st.takeItems(192,1)
            st.takeItems(4634,1)
            st.takeItems(2131,250)
            st.giveItems(4867,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "63":
        if st.getQuestItemsCount(192) == 1 and st.getQuestItemsCount(4645) == 1 and st.getQuestItemsCount(2131) >= 250 :
            st.takeItems(192,1)
            st.takeItems(4645,1)
            st.takeItems(2131,250)
            st.giveItems(4868,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "64":
        if st.getQuestItemsCount(192) == 1 and st.getQuestItemsCount(4656) == 1 and st.getQuestItemsCount(2131) >= 250 :
            st.takeItems(192,1)
            st.takeItems(4656,1)
            st.takeItems(2131,250)
            st.giveItems(4869,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "65":
        if st.getQuestItemsCount(195) == 1 and st.getQuestItemsCount(4635) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(195,1)
            st.takeItems(4635,1)
            st.takeItems(2131,350)
            st.giveItems(4873,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "66":
        if st.getQuestItemsCount(195) == 1 and st.getQuestItemsCount(4646) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(195,1)
            st.takeItems(4646,1)
            st.takeItems(2131,350)
            st.giveItems(4874,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "67":
        if st.getQuestItemsCount(195) == 1 and st.getQuestItemsCount(4657) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(195,1)
            st.takeItems(4657,1)
            st.takeItems(2131,350)
            st.giveItems(4875,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "68":
        if st.getQuestItemsCount(197) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(197,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4876,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "69":
        if st.getQuestItemsCount(197) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(197,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4877,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "70":
        if st.getQuestItemsCount(197) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(197,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4878,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "71":
        if st.getQuestItemsCount(200) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(200,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4882,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "72":
        if st.getQuestItemsCount(200) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(200,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4883,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "73":
        if st.getQuestItemsCount(200) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(200,1)
            st.takeItems(4658,1)
            st.takeItems(57,600000)
            st.giveItems(4884,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "74":
        if st.getQuestItemsCount(203) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(203,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4885,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "75":
        if st.getQuestItemsCount(203) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(203,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4886,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "76":
        if st.getQuestItemsCount(203) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(203,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4887,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "77":
        if st.getQuestItemsCount(205) == 1 and st.getQuestItemsCount(4637) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(205,1)
            st.takeItems(4637,1)
            st.takeItems(2131,550)
            st.giveItems(4891,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "78":
        if st.getQuestItemsCount(205) == 1 and st.getQuestItemsCount(4648) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(205,1)
            st.takeItems(4648,1)
            st.takeItems(2131,550)
            st.giveItems(4892,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "79":
        if st.getQuestItemsCount(205) == 1 and st.getQuestItemsCount(4659) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(205,1)
            st.takeItems(4659,1)
            st.takeItems(2131,550)
            st.giveItems(4893,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "80":
        if st.getQuestItemsCount(206) == 1 and st.getQuestItemsCount(4637) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(206,1)
            st.takeItems(4637,1)
            st.takeItems(2131,550)
            st.giveItems(4894,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "81":
        if st.getQuestItemsCount(206) == 1 and st.getQuestItemsCount(4648) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(206,1)
            st.takeItems(4648,1)
            st.takeItems(2131,550)
            st.giveItems(4895,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "82":
        if st.getQuestItemsCount(206) == 1 and st.getQuestItemsCount(4659) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(206,1)
            st.takeItems(4659,1)
            st.takeItems(2131,550)
            st.giveItems(4896,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "83":
        if st.getQuestItemsCount(204) == 1 and st.getQuestItemsCount(4637) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(204,1)
            st.takeItems(4637,1)
            st.takeItems(2131,550)
            st.giveItems(4888,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "84":
        if st.getQuestItemsCount(204) == 1 and st.getQuestItemsCount(4648) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(204,1)
            st.takeItems(4648,1)
            st.takeItems(2131,550)
            st.giveItems(4889,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "85":
        if st.getQuestItemsCount(204) == 1 and st.getQuestItemsCount(4659) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(204,1)
            st.takeItems(4659,1)
            st.takeItems(2131,550)
            st.giveItems(4890,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "86":
        if st.getQuestItemsCount(92) == 1 and st.getQuestItemsCount(4638) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(92,1)
            st.takeItems(4638,1)
            st.takeItems(2132,222)
            st.giveItems(4897,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "87":
        if st.getQuestItemsCount(92) == 1 and st.getQuestItemsCount(4649) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(92,1)
            st.takeItems(4649,1)
            st.takeItems(2132,222)
            st.giveItems(4898,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "88":
        if st.getQuestItemsCount(92) == 1 and st.getQuestItemsCount(4660) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(92,1)
            st.takeItems(4660,1)
            st.takeItems(2132,222)
            st.giveItems(4899,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "89":
        if st.getQuestItemsCount(210) == 1 and st.getQuestItemsCount(4639) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(210,1)
            st.takeItems(4639,1)
            st.takeItems(2132,339)
            st.giveItems(4900,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "90":
        if st.getQuestItemsCount(210) == 1 and st.getQuestItemsCount(4650) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(210,1)
            st.takeItems(4650,1)
            st.takeItems(2132,339)
            st.giveItems(4901,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "91":
        if st.getQuestItemsCount(210) == 1 and st.getQuestItemsCount(4661) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(210,1)
            st.takeItems(4661,1)
            st.takeItems(2132,339)
            st.giveItems(4902,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "92":
        if st.getQuestItemsCount(91) == 1 and st.getQuestItemsCount(4638) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(91,1)
            st.takeItems(4638,1)
            st.takeItems(2132,222)
            st.giveItems(4747,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "93":
        if st.getQuestItemsCount(91) == 1 and st.getQuestItemsCount(4649) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(91,1)
            st.takeItems(4649,1)
            st.takeItems(2132,222)
            st.giveItems(4748,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "94":
        if st.getQuestItemsCount(91) == 1 and st.getQuestItemsCount(4660) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(91,1)
            st.takeItems(4660,1)
            st.takeItems(2132,222)
            st.giveItems(4749,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "95":
        if st.getQuestItemsCount(175) == 1 and st.getQuestItemsCount(4639) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(175,1)
            st.takeItems(4639,1)
            st.takeItems(2132,339)
            st.giveItems(4753,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "96":
        if st.getQuestItemsCount(175) == 1 and st.getQuestItemsCount(4650) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(175,1)
            st.takeItems(4650,1)
            st.takeItems(2132,339)
            st.giveItems(4754,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "97":
        if st.getQuestItemsCount(175) == 1 and st.getQuestItemsCount(4661) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(175,1)
            st.takeItems(4661,1)
            st.takeItems(2132,339)
            st.giveItems(4755,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "98":
        if st.getQuestItemsCount(171) == 1 and st.getQuestItemsCount(4639) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(171,1)
            st.takeItems(4639,1)
            st.takeItems(2132,339)
            st.giveItems(4750,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "99":
        if st.getQuestItemsCount(171) == 1 and st.getQuestItemsCount(4650) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(171,1)
            st.takeItems(4650,1)
            st.takeItems(2132,339)
            st.giveItems(4751,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "100":
        if st.getQuestItemsCount(171) == 1 and st.getQuestItemsCount(4661) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(171,1)
            st.takeItems(4661,1)
            st.takeItems(2132,339)
            st.giveItems(4752,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    #Daggers
    if event == "101":
        if st.getQuestItemsCount(228) == 1 and st.getQuestItemsCount(4637) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(228,1)
            st.takeItems(4637,1)
            st.takeItems(2131,550)
            st.giveItems(4774,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "102":
        if st.getQuestItemsCount(228) == 1 and st.getQuestItemsCount(4648) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(228,1)
            st.takeItems(4648,1)
            st.takeItems(2131,550)
            st.giveItems(4775,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "103":
        if st.getQuestItemsCount(228) == 1 and st.getQuestItemsCount(4659) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(228,1)
            st.takeItems(4659,1)
            st.takeItems(2131,550)
            st.giveItems(4776,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "104":
        if st.getQuestItemsCount(233) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(233,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4771,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "105":
        if st.getQuestItemsCount(233) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(233,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4772,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "106":
        if st.getQuestItemsCount(233) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(233,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4773,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "107":
        if st.getQuestItemsCount(231) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(231,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4768,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "108":
        if st.getQuestItemsCount(231) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(231,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4769,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "109":
        if st.getQuestItemsCount(231) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(231,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4770,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "110":
        if st.getQuestItemsCount(229) == 1 and st.getQuestItemsCount(4638) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(229,1)
            st.takeItems(4638,1)
            st.takeItems(2132,222)
            st.giveItems(4777,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "111":
        if st.getQuestItemsCount(229) == 1 and st.getQuestItemsCount(4649) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(229,1)
            st.takeItems(4649,1)
            st.takeItems(2132,222)
            st.giveItems(4778,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "112":
        if st.getQuestItemsCount(229) == 1 and st.getQuestItemsCount(4660) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(229,1)
            st.takeItems(4660,1)
            st.takeItems(2132,222)
            st.giveItems(4779,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "113":
        if st.getQuestItemsCount(234) == 1 and st.getQuestItemsCount(4639) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(234,1)
            st.takeItems(4639,1)
            st.takeItems(2132,339)
            st.giveItems(4780,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "114":
        if st.getQuestItemsCount(234) == 1 and st.getQuestItemsCount(4650) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(234,1)
            st.takeItems(4650,1)
            st.takeItems(2132,339)
            st.giveItems(4781,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "115":
        if st.getQuestItemsCount(234) == 1 and st.getQuestItemsCount(4661) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(234,1)
            st.takeItems(4661,1)
            st.takeItems(2132,339)
            st.giveItems(4782,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    #PoleArms
    if event == "116":
        if st.getQuestItemsCount(301) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(301,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4846,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "117":
        if st.getQuestItemsCount(301) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(301,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4847,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "118":
        if st.getQuestItemsCount(301) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(301,1)
            st.takeItems(4659,1)
            st.takeItems(2131,450)
            st.giveItems(4848,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "119":
        if st.getQuestItemsCount(303) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(303,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4849,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "120":
        if st.getQuestItemsCount(303) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(303,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4850,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "121":
        if st.getQuestItemsCount(303) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(303,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4851,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "122":
        if st.getQuestItemsCount(299) == 1 and st.getQuestItemsCount(4637) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(299,1)
            st.takeItems(4637,1)
            st.takeItems(2131,550)
            st.giveItems(4852,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "123":
        if st.getQuestItemsCount(299) == 1 and st.getQuestItemsCount(4648) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(299,1)
            st.takeItems(4648,1)
            st.takeItems(2131,550)
            st.giveItems(4853,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "124":
        if st.getQuestItemsCount(299) == 1 and st.getQuestItemsCount(4659) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(299,1)
            st.takeItems(4659,1)
            st.takeItems(2131,550)
            st.giveItems(4854,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "125":
        if st.getQuestItemsCount(300) == 1 and st.getQuestItemsCount(4638) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(300,1)
            st.takeItems(4638,1)
            st.takeItems(2132,222)
            st.giveItems(4855,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "126":
        if st.getQuestItemsCount(300) == 1 and st.getQuestItemsCount(4649) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(300,1)
            st.takeItems(4649,1)
            st.takeItems(2132,222)
            st.giveItems(4856,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "127":
        if st.getQuestItemsCount(300) == 1 and st.getQuestItemsCount(4660) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(300,1)
            st.takeItems(4660,1)
            st.takeItems(2132,222)
            st.giveItems(4857,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "128":
        if st.getQuestItemsCount(97) == 1 and st.getQuestItemsCount(4639) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(97,1)
            st.takeItems(4639,1)
            st.takeItems(2132,339)
            st.giveItems(4858,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "129":
        if st.getQuestItemsCount(97) == 1 and st.getQuestItemsCount(4650) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(97,1)
            st.takeItems(4650,1)
            st.takeItems(2132,339)
            st.giveItems(4859,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "130":
        if st.getQuestItemsCount(97) == 1 and st.getQuestItemsCount(4661) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(97,1)
            st.takeItems(4661,1)
            st.takeItems(2132,339)
            st.giveItems(4860,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    #Fists
    if event == "131":
        if st.getQuestItemsCount(265) == 1 and st.getQuestItemsCount(4635) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(265,1)
            st.takeItems(4635,1)
            st.takeItems(2131,350)
            st.giveItems(4792,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "132":
        if st.getQuestItemsCount(265) == 1 and st.getQuestItemsCount(4646) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(265,1)
            st.takeItems(4646,1)
            st.takeItems(2131,350)
            st.giveItems(4793,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "133":
        if st.getQuestItemsCount(265) == 1 and st.getQuestItemsCount(4657) == 1 and st.getQuestItemsCount(2131) >= 350 :
            st.takeItems(265,1)
            st.takeItems(4657,1)
            st.takeItems(2131,350)
            st.giveItems(4794,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "134":
        if st.getQuestItemsCount(266) == 1 and st.getQuestItemsCount(4637) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(266,1)
            st.takeItems(4637,1)
            st.takeItems(2131,550)
            st.giveItems(4795,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "135":
        if st.getQuestItemsCount(266) == 1 and st.getQuestItemsCount(4648) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(266,1)
            st.takeItems(4648,1)
            st.takeItems(2131,550)
            st.giveItems(4796,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "136":
        if st.getQuestItemsCount(266) == 1 and st.getQuestItemsCount(4659) == 1 and st.getQuestItemsCount(2131) >= 550 :
            st.takeItems(266,1)
            st.takeItems(4659,1)
            st.takeItems(2131,550)
            st.giveItems(4797,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "137":
        if st.getQuestItemsCount(267) == 1 and st.getQuestItemsCount(4638) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(267,1)
            st.takeItems(4638,1)
            st.takeItems(2132,222)
            st.giveItems(4801,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "138":
        if st.getQuestItemsCount(267) == 1 and st.getQuestItemsCount(4649) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(267,1)
            st.takeItems(4649,1)
            st.takeItems(2132,222)
            st.giveItems(4802,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "139":
        if st.getQuestItemsCount(267) == 1 and st.getQuestItemsCount(4660) == 1 and st.getQuestItemsCount(2132) >= 222 :
            st.takeItems(267,1)
            st.takeItems(4660,1)
            st.takeItems(2132,222)
            st.giveItems(4803,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "140":
        if st.getQuestItemsCount(268) == 1 and st.getQuestItemsCount(4639) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(268,1)
            st.takeItems(4639,1)
            st.takeItems(2132,339)
            st.giveItems(4804,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "141":
        if st.getQuestItemsCount(268) == 1 and st.getQuestItemsCount(4650) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(268,1)
            st.takeItems(4650,1)
            st.takeItems(2132,339)
            st.giveItems(4805,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "142":
        if st.getQuestItemsCount(268) == 1 and st.getQuestItemsCount(4661) == 1 and st.getQuestItemsCount(2132) >= 339 :
            st.takeItems(268,1)
            st.takeItems(4661,1)
            st.takeItems(2132,339)
            st.giveItems(4806,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "death":
        if st.getQuestItemsCount(145) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(145,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(6310,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "death1":
        if st.getQuestItemsCount(145) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(145,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(6311,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "death2":
        if st.getQuestItemsCount(145) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(145,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(6312,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "hom":
        if st.getQuestItemsCount(84) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(84,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(6313,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "hom1":
        if st.getQuestItemsCount(84) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(84,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(6314,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "hom2":
        if st.getQuestItemsCount(84) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            st.takeItems(84,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(6315,1)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    return htmltext

 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say to you.</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(COMPLETED)
#   if npcId == 7471 or npcId == 7300 or npcId == 7688 or npcId == 7458 or npcId == 7317 or npcId == 7298 or npcId == 7846 or npcId == 7678 :
#      htmltext = "1.htm"
   return "1.htm"

QUEST       = Quest(1006,"1004_enhance","Blacksmith")
CREATED     = State('Start',     QUEST)
STARTED     = State('Started',   QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7300)
QUEST.addStartNpc(7846)
QUEST.addStartNpc(7678)
QUEST.addStartNpc(7471)

STARTED.addTalkId(7300)
STARTED.addTalkId(7846)
STARTED.addTalkId(7678)
STARTED.addTalkId(7471)
