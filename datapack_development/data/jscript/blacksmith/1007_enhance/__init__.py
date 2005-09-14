print "importing blacksmith data: 1007_enhance"
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
            EnchantLevel2 = st.getEnchantLevel(281)
            st.takeItems(281,1)
            st.takeItems(4634,1)
            st.takeItems(2131,250)
            st.giveItems(4810,1)
            st.setEnchantLevel(4810,EnchantLevel2)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "3":
        if st.getQuestItemsCount(281) == 1 and st.getQuestItemsCount(4645) == 1 and st.getQuestItemsCount(2131) >= 250 :
            EnchantLevel3 = st.getEnchantLevel(281)
            st.takeItems(281,1)
            st.takeItems(4645,1)
            st.takeItems(2131,250)
            st.giveItems(4811,1)
            st.setEnchantLevel(4811,EnchantLevel3)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "4":
        if st.getQuestItemsCount(281) == 1 and st.getQuestItemsCount(4656) == 1 and st.getQuestItemsCount(2131) >= 250 :
            EnchantLevel4 = st.getEnchantLevel(281)
            st.takeItems(281,1)
            st.takeItems(4656,1)
            st.takeItems(2131,250)
            st.giveItems(4812,1)
            st.setEnchantLevel(4812,EnchantLevel4)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "5":
        if st.getQuestItemsCount(285) == 1 and st.getQuestItemsCount(4635) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel5 = st.getEnchantLevel(285)
            st.takeItems(285,1)
            st.takeItems(4635,1)
            st.takeItems(2131,350)
            st.giveItems(4816,1)
            st.setEnchantLevel(4816,EnchantLevel5)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "6":
        if st.getQuestItemsCount(285) == 1 and st.getQuestItemsCount(4646) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel6 = st.getEnchantLevel(285)
            st.takeItems(285,1)
            st.takeItems(4646,1)
            st.takeItems(2131,350)
            st.giveItems(4817,1)
            st.setEnchantLevel(4817,EnchantLevel6)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "7":
        if st.getQuestItemsCount(285) == 1 and st.getQuestItemsCount(4657) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel7 = st.getEnchantLevel(285)
            st.takeItems(285,1)
            st.takeItems(4657,1)
            st.takeItems(2131,350)
            st.giveItems(4818,1)
            st.setEnchantLevel(4818,EnchantLevel7)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "8":
        if st.getQuestItemsCount(283) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel8 = st.getEnchantLevel(283)
            st.takeItems(283,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4819,1)
            st.setEnchantLevel(4819,EnchantLevel8)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "9":
        if st.getQuestItemsCount(283) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel9 = st.getEnchantLevel(283)
            st.takeItems(283,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4820,1)
            st.setEnchantLevel(4820,EnchantLevel9)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "10":
        if st.getQuestItemsCount(283) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel10 = st.getEnchantLevel(283)
            st.takeItems(283,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4821,1)
            st.setEnchantLevel(4821,EnchantLevel10)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "11":
        if st.getQuestItemsCount(286) == 1 and st.getQuestItemsCount(4637) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel11 = st.getEnchantLevel(286)
            st.takeItems(286,1)
            st.takeItems(4637,1)
            st.takeItems(2131,550)
            st.giveItems(4822,1)
            st.setEnchantLevel(4822,EnchantLevel11)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "12":
        if st.getQuestItemsCount(286) == 1 and st.getQuestItemsCount(4648) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel12 = st.getEnchantLevel(286)
            st.takeItems(286,1)
            st.takeItems(4648,1)
            st.takeItems(2131,550)
            st.giveItems(4823,1)
            st.setEnchantLevel(4823,EnchantLevel12)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "13":
        if st.getQuestItemsCount(286) == 1 and st.getQuestItemsCount(4659) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel13 = st.getEnchantLevel(286)
            st.takeItems(286,1)
            st.takeItems(4659,1)
            st.takeItems(2131,550)
            st.giveItems(4824,1)
            st.setEnchantLevel(4824,EnchantLevel13)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "14":
        if st.getQuestItemsCount(284) == 1 and st.getQuestItemsCount(4639) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel14 = st.getEnchantLevel(284)
            st.takeItems(284,1)
            st.takeItems(4639,1)
            st.takeItems(2132,222)
            st.giveItems(4825,1)
            st.setEnchantLevel(4825,EnchantLevel14)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "15":
        if st.getQuestItemsCount(284) == 1 and st.getQuestItemsCount(4650) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel15 = st.getEnchantLevel(284)
            st.takeItems(284,1)
            st.takeItems(4650,1)
            st.takeItems(2132,222)
            st.giveItems(4826,1)
            st.setEnchantLevel(4826,EnchantLevel15)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "16":
        if st.getQuestItemsCount(284) == 1 and st.getQuestItemsCount(4661) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel16 = st.getEnchantLevel(284)
            st.takeItems(284,1)
            st.takeItems(4661,1)
            st.takeItems(2132,222)
            st.giveItems(4827,1)
            st.setEnchantLevel(4827,EnchantLevel16)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "17":
        if st.getQuestItemsCount(287) == 1 and st.getQuestItemsCount(4639) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel17 = st.getEnchantLevel(287)
            st.takeItems(287,1)
            st.takeItems(4639,1)
            st.takeItems(2132,339)
            st.giveItems(4828,1)
            st.setEnchantLevel(4828,EnchantLevel17)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "18":
        if st.getQuestItemsCount(287) == 1 and st.getQuestItemsCount(4650) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel18 = st.getEnchantLevel(287)
            st.takeItems(287,1)
            st.takeItems(4650,1)
            st.takeItems(2132,339)
            st.giveItems(4829,1)
            st.setEnchantLevel(4829,EnchantLevel18)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "19":
        if st.getQuestItemsCount(287) == 1 and st.getQuestItemsCount(4661) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel19 = st.getEnchantLevel(287)
            st.takeItems(287,1)
            st.takeItems(4661,1)
            st.takeItems(2132,339)
            st.giveItems(4830,1)
            st.setEnchantLevel(4830,EnchantLevel19)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    #Swords
    if event == "20":
        if st.getQuestItemsCount(72) == 1 and st.getQuestItemsCount(4634) == 1 and st.getQuestItemsCount(2131) >= 250 :
            EnchantLevel20 = st.getEnchantLevel(72)
            st.takeItems(72,1)
            st.takeItems(4634,1)
            st.takeItems(2131,250)
            st.giveItems(4681,1)
            st.setEnchantLevel(4681,EnchantLevel20)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "21":
        if st.getQuestItemsCount(72) == 1 and st.getQuestItemsCount(4645) == 1 and st.getQuestItemsCount(2131) >= 250 :
            EnchantLevel21 = st.getEnchantLevel(72)
            st.takeItems(72,1)
            st.takeItems(4645,1)
            st.takeItems(2131,250)
            st.giveItems(4682,1)
            st.setEnchantLevel(4682,EnchantLevel21)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "22":
        if st.getQuestItemsCount(72) == 1 and st.getQuestItemsCount(4656) == 1 and st.getQuestItemsCount(2131) >= 250 :
            EnchantLevel22 = st.getEnchantLevel(72)
            st.takeItems(72,1)
            st.takeItems(4656,1)
            st.takeItems(2131,250)
            st.giveItems(4683,1)
            st.setEnchantLevel(4683,EnchantLevel22)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "23":
        if st.getQuestItemsCount(74) == 1 and st.getQuestItemsCount(4635) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel23 = st.getEnchantLevel(74)
            st.takeItems(74,1)
            st.takeItems(4635,1)
            st.takeItems(2131,350)
            st.giveItems(4687,1)
            st.setEnchantLevel(4687,EnchantLevel23)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "24":
        if st.getQuestItemsCount(74) == 1 and st.getQuestItemsCount(4646) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel24 = st.getEnchantLevel(74)
            st.takeItems(74,1)
            st.takeItems(4646,1)
            st.takeItems(2131,350)
            st.giveItems(4688,1)
            st.setEnchantLevel(4688,EnchantLevel24)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "25":
        if st.getQuestItemsCount(74) == 1 and st.getQuestItemsCount(4657) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel25 = st.getEnchantLevel(74)
            st.takeItems(74,1)
            st.takeItems(4657,1)
            st.takeItems(2131,350)
            st.giveItems(4689,1)
            st.setEnchantLevel(4689,EnchantLevel25)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "26":
        if st.getQuestItemsCount(131) == 1 and st.getQuestItemsCount(4635) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel26 = st.getEnchantLevel(131)
            st.takeItems(131,1)
            st.takeItems(4635,1)
            st.takeItems(2131,350)
            st.giveItems(4690,1)
            st.setEnchantLevel(4690,EnchantLevel26)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "27":
        if st.getQuestItemsCount(131) == 1 and st.getQuestItemsCount(4646) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel27 = st.getEnchantLevel(131)
            st.takeItems(131,1)
            st.takeItems(4646,1)
            st.takeItems(2131,350)
            st.giveItems(4691,1)
            st.setEnchantLevel(4691,EnchantLevel27)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "28":
        if st.getQuestItemsCount(131) == 1 and st.getQuestItemsCount(4657) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel28 = st.getEnchantLevel(131)
            st.takeItems(131,1)
            st.takeItems(4657,1)
            st.takeItems(2131,350)
            st.giveItems(4692,1)
            st.setEnchantLevel(4692,EnchantLevel28)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "29":
        if st.getQuestItemsCount(133) == 1 and st.getQuestItemsCount(4635) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel29 = st.getEnchantLevel(133)
            st.takeItems(133,1)
            st.takeItems(4635,1)
            st.takeItems(2131,350)
            st.giveItems(4693,1)
            st.setEnchantLevel(4693,EnchantLevel29)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "30":
        if st.getQuestItemsCount(133) == 1 and st.getQuestItemsCount(4646) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel30 = st.getEnchantLevel(133)
            st.takeItems(133,1)
            st.takeItems(4646,1)
            st.takeItems(2131,350)
            st.giveItems(4694,1)
            st.setEnchantLevel(4694,EnchantLevel30)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "31":
        if st.getQuestItemsCount(133) == 1 and st.getQuestItemsCount(4657) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel31 = st.getEnchantLevel(133)
            st.takeItems(133,1)
            st.takeItems(4657,1)
            st.takeItems(2131,350)
            st.giveItems(4695,1)
            st.setEnchantLevel(4695,EnchantLevel31)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "32":
        if st.getQuestItemsCount(73) == 1 and st.getQuestItemsCount(4635) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel32 = st.getEnchantLevel(73)
            st.takeItems(73,1)
            st.takeItems(4635,1)
            st.takeItems(2131,350)
            st.giveItems(4684,1)
            st.setEnchantLevel(4684,EnchantLevel32)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "33":
        if st.getQuestItemsCount(73) == 1 and st.getQuestItemsCount(4646) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel33 = st.getEnchantLevel(73)
            st.takeItems(73,1)
            st.takeItems(4646,1)
            st.takeItems(2131,350)
            st.giveItems(4685,1)
            st.setEnchantLevel(4685,EnchantLevel33)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "34":
        if st.getQuestItemsCount(73) == 1 and st.getQuestItemsCount(4657) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel34 = st.getEnchantLevel(73)
            st.takeItems(73,1)
            st.takeItems(4657,1)
            st.takeItems(2131,350)
            st.giveItems(4686,1)
            st.setEnchantLevel(4686,EnchantLevel34)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "35":
        if st.getQuestItemsCount(76) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel35 = st.getEnchantLevel(76)
            st.takeItems(76,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4699,1)
            st.setEnchantLevel(4699,EnchantLevel35)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "36":
        if st.getQuestItemsCount(76) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel36 = st.getEnchantLevel(76)
            st.takeItems(76,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4700,1)
            st.setEnchantLevel(4700,EnchantLevel36)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "37":
        if st.getQuestItemsCount(76) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel37 = st.getEnchantLevel(76)
            st.takeItems(76,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4701,1)
            st.setEnchantLevel(4701,EnchantLevel37)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "38":
        if st.getQuestItemsCount(77) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel38 = st.getEnchantLevel(77)
            st.takeItems(77,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4702,1)
            st.setEnchantLevel(4702,EnchantLevel38)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "39":
        if st.getQuestItemsCount(77) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel39 = st.getEnchantLevel(77)
            st.takeItems(77,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4703,1)
            st.setEnchantLevel(4703,EnchantLevel39)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "40":
        if st.getQuestItemsCount(77) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel40 = st.getEnchantLevel(77)
            st.takeItems(77,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4704,1)
            st.setEnchantLevel(4704,EnchantLevel40)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "41":
        if st.getQuestItemsCount(134) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel41 = st.getEnchantLevel(134)
            st.takeItems(134,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4705,1)
            st.setEnchantLevel(4705,EnchantLevel41)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "42":
        if st.getQuestItemsCount(134) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel42 = st.getEnchantLevel(134)
            st.takeItems(134,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4706,1)
            st.setEnchantLevel(4706,EnchantLevel42)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "43":
        if st.getQuestItemsCount(134) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel43 = st.getEnchantLevel(134)
            st.takeItems(134,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4707,1)
            st.setEnchantLevel(4707,EnchantLevel43)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "44":
        if st.getQuestItemsCount(132) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel44 = st.getEnchantLevel(132)
            st.takeItems(132,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(6307,1)
            st.setEnchantLevel(6307,EnchantLevel44)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "45":
        if st.getQuestItemsCount(132) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel45 = st.getEnchantLevel(132)
            st.takeItems(132,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(6308,1)
            st.setEnchantLevel(6308,EnchantLevel45)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "46":
        if st.getQuestItemsCount(132) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel46 = st.getEnchantLevel(132)
            st.takeItems(132,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(6309,1)
            st.setEnchantLevel(6309,EnchantLevel46)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "47":
        if st.getQuestItemsCount(79) == 1 and st.getQuestItemsCount(4639) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel47 = st.getEnchantLevel(79)
            st.takeItems(79,1)
            st.takeItems(4639,1)
            st.takeItems(2132,339)
            st.giveItems(4717,1)
            st.setEnchantLevel(4717,EnchantLevel47)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "48":
        if st.getQuestItemsCount(79) == 1 and st.getQuestItemsCount(4650) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel48 = st.getEnchantLevel(79)
            st.takeItems(79,1)
            st.takeItems(4650,1)
            st.takeItems(2132,339)
            st.giveItems(4718,1)
            st.setEnchantLevel(4718,EnchantLevel48)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "49":
        if st.getQuestItemsCount(79) == 1 and st.getQuestItemsCount(4661) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel49 = st.getEnchantLevel(79)
            st.takeItems(79,1)
            st.takeItems(4661,1)
            st.takeItems(2132,339)
            st.giveItems(4719,1)
            st.setEnchantLevel(4719,EnchantLevel49)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "50":
        if st.getQuestItemsCount(142) == 1 and st.getQuestItemsCount(4638) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel50 = st.getEnchantLevel(142)
            st.takeItems(142,1)
            st.takeItems(4638,1)
            st.takeItems(2132,222)
            st.giveItems(4714,1)
            st.setEnchantLevel(4714,EnchantLevel50)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "51":
        if st.getQuestItemsCount(142) == 1 and st.getQuestItemsCount(4649) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel51 = st.getEnchantLevel(142)
            st.takeItems(142,1)
            st.takeItems(4649,1)
            st.takeItems(2132,222)
            st.giveItems(4715,1)
            st.setEnchantLevel(4715,EnchantLevel51)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "52":
        if st.getQuestItemsCount(142) == 1 and st.getQuestItemsCount(4660) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel52 = st.getEnchantLevel(142)
            st.takeItems(142,1)
            st.takeItems(4660,1)
            st.takeItems(2132,222)
            st.giveItems(4716,1)
            st.setEnchantLevel(4716,EnchantLevel52)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "53":
        if st.getQuestItemsCount(78) == 1 and st.getQuestItemsCount(4638) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel53 = st.getEnchantLevel(78)
            st.takeItems(78,1)
            st.takeItems(4638,1)
            st.takeItems(2132,222)
            st.giveItems(4723,1)
            st.setEnchantLevel(4723,EnchantLevel53)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "54":
        if st.getQuestItemsCount(78) == 1 and st.getQuestItemsCount(4649) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel54 = st.getEnchantLevel(78)
            st.takeItems(78,1)
            st.takeItems(4649,1)
            st.takeItems(2132,222)
            st.giveItems(4724,1)
            st.setEnchantLevel(4724,EnchantLevel54)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "55":
        if st.getQuestItemsCount(78) == 1 and st.getQuestItemsCount(4660) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel55 = st.getEnchantLevel(78)
            st.takeItems(78,1)
            st.takeItems(4660,1)
            st.takeItems(2132,222)
            st.giveItems(4725,1)
            st.setEnchantLevel(4725,EnchantLevel55)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    #Blunts
    if event == "56":
        if st.getQuestItemsCount(162) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel56 = st.getEnchantLevel(162)
            st.takeItems(162,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4741,1)
            st.setEnchantLevel(4741,EnchantLevel56)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "57":
        if st.getQuestItemsCount(162) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel57 = st.getEnchantLevel(162)
            st.takeItems(162,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4742,1)
            st.setEnchantLevel(4742,EnchantLevel57)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "58":
        if st.getQuestItemsCount(162) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel58 = st.getEnchantLevel(162)
            st.takeItems(162,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4743,1)
            st.setEnchantLevel(4732,EnchantLevel58)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "59":
        if st.getQuestItemsCount(2503) == 1 and st.getQuestItemsCount(4637) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel59 = st.getEnchantLevel(2503)
            st.takeItems(2503,1)
            st.takeItems(4637,1)
            st.takeItems(2131,550)
            st.giveItems(4744,1)
            st.setEnchantLevel(4744,EnchantLevel59)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "60":
        if st.getQuestItemsCount(2503) == 1 and st.getQuestItemsCount(4648) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel60 = st.getEnchantLevel(2503)
            st.takeItems(2503,1)
            st.takeItems(4648,1)
            st.takeItems(2131,550)
            st.giveItems(4745,1)
            st.setEnchantLevel(4745,EnchantLevel60)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "61":
        if st.getQuestItemsCount(2503) == 1 and st.getQuestItemsCount(4659) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel61 = st.getEnchantLevel(2503)
            st.takeItems(2503,1)
            st.takeItems(4659,1)
            st.takeItems(2131,550)
            st.giveItems(4746,1)
            st.setEnchantLevel(4746,EnchantLevel61)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "62":
        if st.getQuestItemsCount(192) == 1 and st.getQuestItemsCount(4634) == 1 and st.getQuestItemsCount(2131) >= 250 :
            EnchantLevel62 = st.getEnchantLevel(192)
            st.takeItems(192,1)
            st.takeItems(4634,1)
            st.takeItems(2131,250)
            st.giveItems(4867,1)
            st.setEnchantLevel(4867,EnchantLevel62)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "63":
        if st.getQuestItemsCount(192) == 1 and st.getQuestItemsCount(4645) == 1 and st.getQuestItemsCount(2131) >= 250 :
            EnchantLevel63 = st.getEnchantLevel(192)
            st.takeItems(192,1)
            st.takeItems(4645,1)
            st.takeItems(2131,250)
            st.giveItems(4868,1)
            st.setEnchantLevel(4868,EnchantLevel63)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "64":
        if st.getQuestItemsCount(192) == 1 and st.getQuestItemsCount(4656) == 1 and st.getQuestItemsCount(2131) >= 250 :
            EnchantLevel64 = st.getEnchantLevel(192)
            st.takeItems(192,1)
            st.takeItems(4656,1)
            st.takeItems(2131,250)
            st.giveItems(4869,1)
            st.setEnchantLevel(4869,EnchantLevel64)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "65":
        if st.getQuestItemsCount(195) == 1 and st.getQuestItemsCount(4635) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel65 = st.getEnchantLevel(195)
            st.takeItems(195,1)
            st.takeItems(4635,1)
            st.takeItems(2131,350)
            st.giveItems(4873,1)
            st.setEnchantLevel(4873,EnchantLevel65)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "66":
        if st.getQuestItemsCount(195) == 1 and st.getQuestItemsCount(4646) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel66 = st.getEnchantLevel(195)
            st.takeItems(195,1)
            st.takeItems(4646,1)
            st.takeItems(2131,350)
            st.giveItems(4874,1)
            st.setEnchantLevel(4874,EnchantLevel66)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "67":
        if st.getQuestItemsCount(195) == 1 and st.getQuestItemsCount(4657) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel67 = st.getEnchantLevel(195)
            st.takeItems(195,1)
            st.takeItems(4657,1)
            st.takeItems(2131,350)
            st.giveItems(4875,1)
            st.setEnchantLevel(4875,EnchantLevel67)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "68":
        if st.getQuestItemsCount(197) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel68 = st.getEnchantLevel(197)
            st.takeItems(197,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4876,1)
            st.setEnchantLevel(4876,EnchantLevel68)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "69":
        if st.getQuestItemsCount(197) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel69 = st.getEnchantLevel(197)
            st.takeItems(197,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4877,1)
            st.setEnchantLevel(4877,EnchantLevel69)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "70":
        if st.getQuestItemsCount(197) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel70 = st.getEnchantLevel(197)
            st.takeItems(197,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4878,1)
            st.setEnchantLevel(4878,EnchantLevel70)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "71":
        if st.getQuestItemsCount(200) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel71 = st.getEnchantLevel(200)
            st.takeItems(200,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4882,1)
            st.setEnchantLevel(4882,EnchantLevel71)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "72":
        if st.getQuestItemsCount(200) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel72 = st.getEnchantLevel(200)
            st.takeItems(200,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4883,1)
            st.setEnchantLevel(4883,EnchantLevel72)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "73":
        if st.getQuestItemsCount(200) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel73 = st.getEnchantLevel(200)
            st.takeItems(200,1)
            st.takeItems(4658,1)
            st.takeItems(57,600000)
            st.giveItems(4884,1)
            st.setEnchantLevel(4884,EnchantLevel73)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "74":
        if st.getQuestItemsCount(203) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel74 = st.getEnchantLevel(203)
            st.takeItems(203,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4885,1)
            st.setEnchantLevel(4885,EnchantLevel74)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "75":
        if st.getQuestItemsCount(203) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel75 = st.getEnchantLevel(203)
            st.takeItems(203,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4886,1)
            st.setEnchantLevel(4886,EnchantLevel75)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "76":
        if st.getQuestItemsCount(203) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel76 = st.getEnchantLevel(203)
            st.takeItems(203,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4887,1)
            st.setEnchantLevel(4887,EnchantLevel76)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "77":
        if st.getQuestItemsCount(205) == 1 and st.getQuestItemsCount(4637) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel77 = st.getEnchantLevel(205)
            st.takeItems(205,1)
            st.takeItems(4637,1)
            st.takeItems(2131,550)
            st.giveItems(4891,1)
            st.setEnchantLevel(4891,EnchantLevel77)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "78":
        if st.getQuestItemsCount(205) == 1 and st.getQuestItemsCount(4648) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel78 = st.getEnchantLevel(205)
            st.takeItems(205,1)
            st.takeItems(4648,1)
            st.takeItems(2131,550)
            st.giveItems(4892,1)
            st.setEnchantLevel(4892,EnchantLevel78)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "79":
        if st.getQuestItemsCount(205) == 1 and st.getQuestItemsCount(4659) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel79 = st.getEnchantLevel(205)
            st.takeItems(205,1)
            st.takeItems(4659,1)
            st.takeItems(2131,550)
            st.giveItems(4893,1)
            st.setEnchantLevel(4893,EnchantLevel79)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "80":
        if st.getQuestItemsCount(206) == 1 and st.getQuestItemsCount(4637) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel80 = st.getEnchantLevel(206)
            st.takeItems(206,1)
            st.takeItems(4637,1)
            st.takeItems(2131,550)
            st.giveItems(4894,1)
            st.setEnchantLevel(4894,EnchantLevel80)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "81":
        if st.getQuestItemsCount(206) == 1 and st.getQuestItemsCount(4648) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel81 = st.getEnchantLevel(206)
            st.takeItems(206,1)
            st.takeItems(4648,1)
            st.takeItems(2131,550)
            st.giveItems(4895,1)
            st.setEnchantLevel(4895,EnchantLevel81)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "82":
        if st.getQuestItemsCount(206) == 1 and st.getQuestItemsCount(4659) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel82 = st.getEnchantLevel(206)
            st.takeItems(206,1)
            st.takeItems(4659,1)
            st.takeItems(2131,550)
            st.giveItems(4896,1)
            st.setEnchantLevel(4896,EnchantLevel82)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "83":
        if st.getQuestItemsCount(204) == 1 and st.getQuestItemsCount(4637) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel83 = st.getEnchantLevel(204)
            st.takeItems(204,1)
            st.takeItems(4637,1)
            st.takeItems(2131,550)
            st.giveItems(4888,1)
            st.setEnchantLevel(4888,EnchantLevel83)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "84":
        if st.getQuestItemsCount(204) == 1 and st.getQuestItemsCount(4648) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel84 = st.getEnchantLevel(204)
            st.takeItems(204,1)
            st.takeItems(4648,1)
            st.takeItems(2131,550)
            st.giveItems(4889,1)
            st.setEnchantLevel(4889,EnchantLevel84)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "85":
        if st.getQuestItemsCount(204) == 1 and st.getQuestItemsCount(4659) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel85 = st.getEnchantLevel(204)
            st.takeItems(204,1)
            st.takeItems(4659,1)
            st.takeItems(2131,550)
            st.giveItems(4890,1)
            st.setEnchantLevel(4890,EnchantLevel85)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "86":
        if st.getQuestItemsCount(92) == 1 and st.getQuestItemsCount(4638) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel86 = st.getEnchantLevel(92)
            st.takeItems(92,1)
            st.takeItems(4638,1)
            st.takeItems(2132,222)
            st.giveItems(4897,1)
            st.setEnchantLevel(4897,EnchantLevel86)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "87":
        if st.getQuestItemsCount(92) == 1 and st.getQuestItemsCount(4649) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel87 = st.getEnchantLevel(92)
            st.takeItems(92,1)
            st.takeItems(4649,1)
            st.takeItems(2132,222)
            st.giveItems(4898,1)
            st.setEnchantLevel(4898,EnchantLevel87)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "88":
        if st.getQuestItemsCount(92) == 1 and st.getQuestItemsCount(4660) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel88 = st.getEnchantLevel(92)
            st.takeItems(92,1)
            st.takeItems(4660,1)
            st.takeItems(2132,222)
            st.giveItems(4899,1)
            st.setEnchantLevel(4899,EnchantLevel88)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "89":
        if st.getQuestItemsCount(210) == 1 and st.getQuestItemsCount(4639) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel89 = st.getEnchantLevel(210)
            st.takeItems(210,1)
            st.takeItems(4639,1)
            st.takeItems(2132,339)
            st.giveItems(4900,1)
            st.setEnchantLevel(4900,EnchantLevel89)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "90":
        if st.getQuestItemsCount(210) == 1 and st.getQuestItemsCount(4650) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel90 = st.getEnchantLevel(210)
            st.takeItems(210,1)
            st.takeItems(4650,1)
            st.takeItems(2132,339)
            st.giveItems(4901,1)
            st.setEnchantLevel(4901,EnchantLevel90)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "91":
        if st.getQuestItemsCount(210) == 1 and st.getQuestItemsCount(4661) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel91 = st.getEnchantLevel(210)
            st.takeItems(210,1)
            st.takeItems(4661,1)
            st.takeItems(2132,339)
            st.giveItems(4902,1)
            st.setEnchantLevel(4902,EnchantLevel91)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "92":
        if st.getQuestItemsCount(91) == 1 and st.getQuestItemsCount(4638) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel92 = st.getEnchantLevel(91)
            st.takeItems(91,1)
            st.takeItems(4638,1)
            st.takeItems(2132,222)
            st.giveItems(4747,1)
            st.setEnchantLevel(4747,EnchantLevel92)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "93":
        if st.getQuestItemsCount(91) == 1 and st.getQuestItemsCount(4649) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel93 = st.getEnchantLevel(91)
            st.takeItems(91,1)
            st.takeItems(4649,1)
            st.takeItems(2132,222)
            st.giveItems(4748,1)
            st.setEnchantLevel(4748,EnchantLevel93)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "94":
        if st.getQuestItemsCount(91) == 1 and st.getQuestItemsCount(4660) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel94 = st.getEnchantLevel(91)
            st.takeItems(91,1)
            st.takeItems(4660,1)
            st.takeItems(2132,222)
            st.giveItems(4749,1)
            st.setEnchantLevel(4749,EnchantLevel94)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "95":
        if st.getQuestItemsCount(175) == 1 and st.getQuestItemsCount(4639) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel95 = st.getEnchantLevel(175)
            st.takeItems(175,1)
            st.takeItems(4639,1)
            st.takeItems(2132,339)
            st.giveItems(4753,1)
            st.setEnchantLevel(4753,EnchantLevel95)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "96":
        if st.getQuestItemsCount(175) == 1 and st.getQuestItemsCount(4650) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel96 = st.getEnchantLevel(175)
            st.takeItems(175,1)
            st.takeItems(4650,1)
            st.takeItems(2132,339)
            st.giveItems(4754,1)
            st.setEnchantLevel(4754,EnchantLevel96)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "97":
        if st.getQuestItemsCount(175) == 1 and st.getQuestItemsCount(4661) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel97 = st.getEnchantLevel(175)
            st.takeItems(175,1)
            st.takeItems(4661,1)
            st.takeItems(2132,339)
            st.giveItems(4755,1)
            st.setEnchantLevel(4755,EnchantLevel97)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "98":
        if st.getQuestItemsCount(171) == 1 and st.getQuestItemsCount(4639) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel98 = st.getEnchantLevel(171)
            st.takeItems(171,1)
            st.takeItems(4639,1)
            st.takeItems(2132,339)
            st.giveItems(4750,1)
            st.setEnchantLevel(4750,EnchantLevel98)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "99":
        if st.getQuestItemsCount(171) == 1 and st.getQuestItemsCount(4650) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel99 = st.getEnchantLevel(171)
            st.takeItems(171,1)
            st.takeItems(4650,1)
            st.takeItems(2132,339)
            st.giveItems(4751,1)
            st.setEnchantLevel(4751,EnchantLevel99)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "100":
        if st.getQuestItemsCount(171) == 1 and st.getQuestItemsCount(4661) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel100 = st.getEnchantLevel(171)
            st.takeItems(171,1)
            st.takeItems(4661,1)
            st.takeItems(2132,339)
            st.giveItems(4752,1)
            st.setEnchantLevel(4752,EnchantLevel100)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    #Daggers
    if event == "101":
        if st.getQuestItemsCount(228) == 1 and st.getQuestItemsCount(4637) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel101 = st.getEnchantLevel(228)
            st.takeItems(228,1)
            st.takeItems(4637,1)
            st.takeItems(2131,550)
            st.giveItems(4774,1)
            st.setEnchantLevel(4774,EnchantLevel101)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "102":
        if st.getQuestItemsCount(228) == 1 and st.getQuestItemsCount(4648) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel102 = st.getEnchantLevel(228)
            st.takeItems(228,1)
            st.takeItems(4648,1)
            st.takeItems(2131,550)
            st.giveItems(4775,1)
            st.setEnchantLevel(4775,EnchantLevel102)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "103":
        if st.getQuestItemsCount(228) == 1 and st.getQuestItemsCount(4659) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel103 = st.getEnchantLevel(228)
            st.takeItems(228,1)
            st.takeItems(4659,1)
            st.takeItems(2131,550)
            st.giveItems(4776,1)
            st.setEnchantLevel(4776,EnchantLevel103)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "104":
        if st.getQuestItemsCount(233) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel104 = st.getEnchantLevel(233)
            st.takeItems(233,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4771,1)
            st.setEnchantLevel(4771,EnchantLevel104)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "105":
        if st.getQuestItemsCount(233) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel105 = st.getEnchantLevel(233)
            st.takeItems(233,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4772,1)
            st.setEnchantLevel(4772,EnchantLevel105)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "106":
        if st.getQuestItemsCount(233) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel106 = st.getEnchantLevel(233)
            st.takeItems(233,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4773,1)
            st.setEnchantLevel(4773,EnchantLevel106)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "107":
        if st.getQuestItemsCount(231) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel107 = st.getEnchantLevel(231)
            st.takeItems(231,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4768,1)
            st.setEnchantLevel(4768,EnchantLevel107)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "108":
        if st.getQuestItemsCount(231) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel108 = st.getEnchantLevel(231)
            st.takeItems(231,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4769,1)
            st.setEnchantLevel(4769,EnchantLevel108)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "109":
        if st.getQuestItemsCount(231) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel109 = st.getEnchantLevel(231)
            st.takeItems(231,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4770,1)
            st.setEnchantLevel(4770,EnchantLevel109)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "110":
        if st.getQuestItemsCount(229) == 1 and st.getQuestItemsCount(4638) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel110 = st.getEnchantLevel(229)
            st.takeItems(229,1)
            st.takeItems(4638,1)
            st.takeItems(2132,222)
            st.giveItems(4777,1)
            st.setEnchantLevel(4777,EnchantLevel110)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "111":
        if st.getQuestItemsCount(229) == 1 and st.getQuestItemsCount(4649) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel111 = st.getEnchantLevel(229)
            st.takeItems(229,1)
            st.takeItems(4649,1)
            st.takeItems(2132,222)
            st.giveItems(4778,1)
            st.setEnchantLevel(4778,EnchantLevel111)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "112":
        if st.getQuestItemsCount(229) == 1 and st.getQuestItemsCount(4660) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel112 = st.getEnchantLevel(229)
            st.takeItems(229,1)
            st.takeItems(4660,1)
            st.takeItems(2132,222)
            st.giveItems(4779,1)
            st.setEnchantLevel(4779,EnchantLevel112)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "113":
        if st.getQuestItemsCount(234) == 1 and st.getQuestItemsCount(4639) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel113 = st.getEnchantLevel(234)
            st.takeItems(234,1)
            st.takeItems(4639,1)
            st.takeItems(2132,339)
            st.giveItems(4780,1)
            st.setEnchantLevel(4780,EnchantLevel113)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "114":
        if st.getQuestItemsCount(234) == 1 and st.getQuestItemsCount(4650) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel114 = st.getEnchantLevel(234)
            st.takeItems(234,1)
            st.takeItems(4650,1)
            st.takeItems(2132,339)
            st.giveItems(4781,1)
            st.setEnchantLevel(4781,EnchantLevel114)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "115":
        if st.getQuestItemsCount(234) == 1 and st.getQuestItemsCount(4661) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel115 = st.getEnchantLevel(234)
            st.takeItems(234,1)
            st.takeItems(4661,1)
            st.takeItems(2132,339)
            st.giveItems(4782,1)
            st.setEnchantLevel(4782,EnchantLevel115)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    #PoleArms
    if event == "116":
        if st.getQuestItemsCount(301) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel116 = st.getEnchantLevel(301)
            st.takeItems(301,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4846,1)
            st.setEnchantLevel(4846,EnchantLevel116)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "117":
        if st.getQuestItemsCount(301) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel117 = st.getEnchantLevel(301)
            st.takeItems(301,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4847,1)
            st.setEnchantLevel(4847,EnchantLevel117)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "118":
        if st.getQuestItemsCount(301) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel118 = st.getEnchantLevel(301)
            st.takeItems(301,1)
            st.takeItems(4659,1)
            st.takeItems(2131,450)
            st.giveItems(4848,1)
            st.setEnchantLevel(4848,EnchantLevel118)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "119":
        if st.getQuestItemsCount(303) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel119 = st.getEnchantLevel(303)
            st.takeItems(303,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(4849,1)
            st.setEnchantLevel(4849,EnchantLevel119)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "120":
        if st.getQuestItemsCount(303) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel120 = st.getEnchantLevel(303)
            st.takeItems(303,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(4850,1)
            st.setEnchantLevel(4850,EnchantLevel120)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "121":
        if st.getQuestItemsCount(303) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel121 = st.getEnchantLevel(303)
            st.takeItems(303,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(4851,1)
            st.setEnchantLevel(4851,EnchantLevel121)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "122":
        if st.getQuestItemsCount(299) == 1 and st.getQuestItemsCount(4637) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel122 = st.getEnchantLevel(299)
            st.takeItems(299,1)
            st.takeItems(4637,1)
            st.takeItems(2131,550)
            st.giveItems(4852,1)
            st.setEnchantLevel(4852,EnchantLevel122)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "123":
        if st.getQuestItemsCount(299) == 1 and st.getQuestItemsCount(4648) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel123 = st.getEnchantLevel(299)
            st.takeItems(299,1)
            st.takeItems(4648,1)
            st.takeItems(2131,550)
            st.giveItems(4853,1)
            st.setEnchantLevel(4853,EnchantLevel123)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "124":
        if st.getQuestItemsCount(299) == 1 and st.getQuestItemsCount(4659) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel124 = st.getEnchantLevel(299)
            st.takeItems(299,1)
            st.takeItems(4659,1)
            st.takeItems(2131,550)
            st.giveItems(4854,1)
            st.setEnchantLevel(4854,EnchantLevel124)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "125":
        if st.getQuestItemsCount(300) == 1 and st.getQuestItemsCount(4638) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel125 = st.getEnchantLevel(300)
            st.takeItems(300,1)
            st.takeItems(4638,1)
            st.takeItems(2132,222)
            st.giveItems(4855,1)
            st.setEnchantLevel(4855,EnchantLevel125)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "126":
        if st.getQuestItemsCount(300) == 1 and st.getQuestItemsCount(4649) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel126 = st.getEnchantLevel(300)
            st.takeItems(300,1)
            st.takeItems(4649,1)
            st.takeItems(2132,222)
            st.giveItems(4856,1)
            st.setEnchantLevel(4856,EnchantLevel126)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "127":
        if st.getQuestItemsCount(300) == 1 and st.getQuestItemsCount(4660) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel127 = st.getEnchantLevel(300)
            st.takeItems(300,1)
            st.takeItems(4660,1)
            st.takeItems(2132,222)
            st.giveItems(4857,1)
            st.setEnchantLevel(4857,EnchantLevel127)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "128":
        if st.getQuestItemsCount(97) == 1 and st.getQuestItemsCount(4639) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel128 = st.getEnchantLevel(97)
            st.takeItems(97,1)
            st.takeItems(4639,1)
            st.takeItems(2132,339)
            st.giveItems(4858,1)
            st.setEnchantLevel(4858,EnchantLevel128)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "129":
        if st.getQuestItemsCount(97) == 1 and st.getQuestItemsCount(4650) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel129 = st.getEnchantLevel(97)
            st.takeItems(97,1)
            st.takeItems(4650,1)
            st.takeItems(2132,339)
            st.giveItems(4859,1)
            st.setEnchantLevel(4859,EnchantLevel129)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "130":
        if st.getQuestItemsCount(97) == 1 and st.getQuestItemsCount(4661) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel130 = st.getEnchantLevel(97)
            st.takeItems(97,1)
            st.takeItems(4661,1)
            st.takeItems(2132,339)
            st.giveItems(4860,1)
            st.setEnchantLevel(4860,EnchantLevel130)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    #Fists
    if event == "131":
        if st.getQuestItemsCount(265) == 1 and st.getQuestItemsCount(4635) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel131 = st.getEnchantLevel(265)
            st.takeItems(265,1)
            st.takeItems(4635,1)
            st.takeItems(2131,350)
            st.giveItems(4792,1)
            st.setEnchantLevel(4792,EnchantLevel131)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "132":
        if st.getQuestItemsCount(265) == 1 and st.getQuestItemsCount(4646) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel132 = st.getEnchantLevel(265)
            st.takeItems(265,1)
            st.takeItems(4646,1)
            st.takeItems(2131,350)
            st.giveItems(4793,1)
            st.setEnchantLevel(4793,EnchantLevel132)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "133":
        if st.getQuestItemsCount(265) == 1 and st.getQuestItemsCount(4657) == 1 and st.getQuestItemsCount(2131) >= 350 :
            EnchantLevel133 = st.getEnchantLevel(265)
            st.takeItems(265,1)
            st.takeItems(4657,1)
            st.takeItems(2131,350)
            st.giveItems(4794,1)
            st.setEnchantLevel(4794,EnchantLevel133)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "134":
        if st.getQuestItemsCount(266) == 1 and st.getQuestItemsCount(4637) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel134 = st.getEnchantLevel(266)
            st.takeItems(266,1)
            st.takeItems(4637,1)
            st.takeItems(2131,550)
            st.giveItems(4795,1)
            st.setEnchantLevel(4795,EnchantLevel134)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "135":
        if st.getQuestItemsCount(266) == 1 and st.getQuestItemsCount(4648) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel135 = st.getEnchantLevel(266)
            st.takeItems(266,1)
            st.takeItems(4648,1)
            st.takeItems(2131,550)
            st.giveItems(4796,1)
            st.setEnchantLevel(4796,EnchantLevel135)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "136":
        if st.getQuestItemsCount(266) == 1 and st.getQuestItemsCount(4659) == 1 and st.getQuestItemsCount(2131) >= 550 :
            EnchantLevel136 = st.getEnchantLevel(266)
            st.takeItems(266,1)
            st.takeItems(4659,1)
            st.takeItems(2131,550)
            st.giveItems(4797,1)
            st.setEnchantLevel(4797,EnchantLevel136)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "137":
        if st.getQuestItemsCount(267) == 1 and st.getQuestItemsCount(4638) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel137 = st.getEnchantLevel(267)
            st.takeItems(267,1)
            st.takeItems(4638,1)
            st.takeItems(2132,222)
            st.giveItems(4801,1)
            st.setEnchantLevel(4801,EnchantLevel137)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "138":
        if st.getQuestItemsCount(267) == 1 and st.getQuestItemsCount(4649) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel138 = st.getEnchantLevel(267)
            st.takeItems(267,1)
            st.takeItems(4649,1)
            st.takeItems(2132,222)
            st.giveItems(4802,1)
            st.setEnchantLevel(4802,EnchantLevel138)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "139":
        if st.getQuestItemsCount(267) == 1 and st.getQuestItemsCount(4660) == 1 and st.getQuestItemsCount(2132) >= 222 :
            EnchantLevel139 = st.getEnchantLevel(267)
            st.takeItems(267,1)
            st.takeItems(4660,1)
            st.takeItems(2132,222)
            st.giveItems(4803,1)
            st.setEnchantLevel(4803,EnchantLevel139)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "140":
        if st.getQuestItemsCount(268) == 1 and st.getQuestItemsCount(4639) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel140 = st.getEnchantLevel(268)
            st.takeItems(268,1)
            st.takeItems(4639,1)
            st.takeItems(2132,339)
            st.giveItems(4804,1)
            st.setEnchantLevel(4804,EnchantLevel140)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "141":
        if st.getQuestItemsCount(268) == 1 and st.getQuestItemsCount(4650) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel141 = st.getEnchantLevel(268)
            st.takeItems(268,1)
            st.takeItems(4650,1)
            st.takeItems(2132,339)
            st.giveItems(4805,1)
            st.setEnchantLevel(4805,EnchantLevel141)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "142":
        if st.getQuestItemsCount(268) == 1 and st.getQuestItemsCount(4661) == 1 and st.getQuestItemsCount(2132) >= 339 :
            EnchantLevel142 = st.getEnchantLevel(268)
            st.takeItems(268,1)
            st.takeItems(4661,1)
            st.takeItems(2132,339)
            st.giveItems(4806,1)
            st.setEnchantLevel(4806,EnchantLevel142)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "death":
        if st.getQuestItemsCount(145) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel143 = st.getEnchantLevel(145)
            st.takeItems(145,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(6310,1)
            st.setEnchantLevel(6310,EnchantLevel143)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "death1":
        if st.getQuestItemsCount(145) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel144 = st.getEnchantLevel(145)
            st.takeItems(145,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(6311,1)
            st.setEnchantLevel(6311,EnchantLevel144)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "death2":
        if st.getQuestItemsCount(145) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel145 = st.getEnchantLevel(145)
            st.takeItems(145,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(6312,1)
            st.setEnchantLevel(6312,EnchantLevel145)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "hom":
        if st.getQuestItemsCount(84) == 1 and st.getQuestItemsCount(4636) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel146 = st.getEnchantLevel(84)
            st.takeItems(84,1)
            st.takeItems(4636,1)
            st.takeItems(2131,450)
            st.giveItems(6313,1)
            st.setEnchantLevel(6313,EnchantLevel146)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "hom1":
        if st.getQuestItemsCount(84) == 1 and st.getQuestItemsCount(4647) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel147 = st.getEnchantLevel(84)
            st.takeItems(84,1)
            st.takeItems(4647,1)
            st.takeItems(2131,450)
            st.giveItems(6314,1)
            st.setEnchantLevel(6314,EnchantLevel147)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    if event == "hom2":
        if st.getQuestItemsCount(84) == 1 and st.getQuestItemsCount(4658) == 1 and st.getQuestItemsCount(2131) >= 450 :
            EnchantLevel148 = st.getEnchantLevel(84)
            st.takeItems(84,1)
            st.takeItems(4658,1)
            st.takeItems(2131,450)
            st.giveItems(6315,1)
            st.setEnchantLevel(6315,EnchantLevel148)
            htmltext = "Item has been succesfully enhanced!"
        else :
            htmltext = "You do not have enough materials."
    
    if htmltext != event:
      st.setState(COMPLETED)
      st.exitQuest(1)

    return htmltext

 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say to you.</body></html>"
   st.setState(STARTED)
   return "1.htm"

QUEST       = Quest(1007,"1007_enhance","Blacksmith")
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
