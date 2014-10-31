import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

SMITHS = [7283,7298,7300,7317,7458,7471,7526,7527,7536,7621,7678,7688,7846,7898,8002,8044,8271,8274,8316,8539,8583,8626,8668]

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event

# Braided Hemp
    if event == "1":
        if st.getQuestItemsCount(2135) >= 1 and st.getQuestItemsCount(1864) >= 5 and st.getQuestItemsCount(57) >= 50 :
            st.takeItems(1864,5)
            st.takeItems(57,50)
            st.giveItems(1878,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Cokes
    if event == "2":
        if st.getQuestItemsCount(2136) >= 1 and st.getQuestItemsCount(1870) >= 3 and st.getQuestItemsCount(1871) >= 3 and st.getQuestItemsCount(57) >= 50:
            st.takeItems(1870,3)
            st.takeItems(1871,3)
            st.takeItems(57,50)
            st.giveItems(1879,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."
# Steel
    if event == "3":
        if st.getQuestItemsCount(2137) >= 1 and st.getQuestItemsCount(1865) >= 5 and st.getQuestItemsCount(1869) >= 5 and st.getQuestItemsCount(57) >= 50:
            st.takeItems(1865,5)
            st.takeItems(1869,5)
            st.takeItems(57,50)
            st.giveItems(1880,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Coarse Bone Powder
    if event == "4":
        if st.getQuestItemsCount(2138) >= 1 and st.getQuestItemsCount(1872) >= 10 and st.getQuestItemsCount(57) >= 50:
            st.takeItems(1872,10)
            st.takeItems(57,50)
            st.giveItems(1881,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Leather
    if event == "5":
        if st.getQuestItemsCount(1814) >= 1 and st.getQuestItemsCount(1867) >= 6 and st.getQuestItemsCount(57) >= 50:
            st.takeItems(1867,6)
            st.takeItems(57,50)
            st.giveItems(1882,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Steel Mold
    if event == "6":
        if st.getQuestItemsCount(2139) >= 1 and st.getQuestItemsCount(1878) >= 5 and st.getQuestItemsCount(1869) >= 5 and st.getQuestItemsCount(1870) >= 5 and st.getQuestItemsCount(57) >= 500:
            st.takeItems(1878,5)
            st.takeItems(1869,5)
            st.takeItems(1870,5)   
            st.takeItems(57,500)
            st.giveItems(1883,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Cord
    if event == "7":
        if st.getQuestItemsCount(1817) >= 1 and st.getQuestItemsCount(1880) >= 2 and st.getQuestItemsCount(1868) >= 25 and st.getQuestItemsCount(57) >= 250:
            st.takeItems(1880,2)
            st.takeItems(1868,25)
            st.takeItems(57,250)
            st.giveItems(1884,20)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# High Grade Suede
    if event == "8":
        if st.getQuestItemsCount(2140) >= 1 and st.getQuestItemsCount(1881) >= 1 and st.getQuestItemsCount(1866) >= 3 and st.getQuestItemsCount(57) >= 250:
            st.takeItems(1881,1)
            st.takeItems(1866,3)
            st.takeItems(57,250)
            st.giveItems(1885,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Silver Mold
    if event == "9":
        if st.getQuestItemsCount(2141) >= 1 and st.getQuestItemsCount(1878) >= 5 and st.getQuestItemsCount(1879) >= 5 and st.getQuestItemsCount(1873) >= 10 and st.getQuestItemsCount(57) >= 500:
            st.takeItems(1878,5)
            st.takeItems(1879,5)
            st.takeItems(1873,10)
            st.takeItems(57,500)
            st.giveItems(1886,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Varnish of Purity
    if event == "10":
        if st.getQuestItemsCount(2142) >= 1 and st.getQuestItemsCount(1875) >= 1 and st.getQuestItemsCount(1881) >= 3 and st.getQuestItemsCount(1865) >= 3 and st.getQuestItemsCount(57) >= 500:
            st.takeItems(1875,1)
            st.takeItems(1881,3)
            st.takeItems(1865,3)
            st.takeItems(57,500)
            st.giveItems(1887,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Synthetic Cokes
    if event == "11":
        if st.getQuestItemsCount(2143) >= 1 and st.getQuestItemsCount(1874) >= 1 and st.getQuestItemsCount(1879) >= 3 and st.getQuestItemsCount(57) >= 500:
            st.takeItems(1874,1)
            st.takeItems(1879,3)
            st.takeItems(57,500)
            st.giveItems(1888,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Compound Braid
    if event == "12":
        if st.getQuestItemsCount(2144) >= 1 and st.getQuestItemsCount(1878) >= 5 and st.getQuestItemsCount(1868) >= 5 and st.getQuestItemsCount(57) >= 250:
            st.takeItems(1878,5)
            st.takeItems(1868,5)
            st.takeItems(57,250)
            st.giveItems(1889,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Mithril Alloy
    if event == "13":
        if st.getQuestItemsCount(2145) >= 1 and st.getQuestItemsCount(1887) >= 1 and st.getQuestItemsCount(1876) >= 1 and st.getQuestItemsCount(1880) >= 2 and st.getQuestItemsCount(57) >= 500:
            st.takeItems(1887,1)
            st.takeItems(1876,1)
            st.takeItems(1880,2)
            st.takeItems(57,500)
            st.giveItems(1890,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Artisans Frame
    if event == "14":
        if st.getQuestItemsCount(2146) >= 1 and st.getQuestItemsCount(1883) >= 1 and st.getQuestItemsCount(1887) >= 3 and st.getQuestItemsCount(1877) >= 10 and st.getQuestItemsCount(57) >= 500:
            st.takeItems(1883,1)
            st.takeItems(1887,5)
            st.takeItems(1877,10)
            st.takeItems(57,500)
            st.giveItems(1891,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."    

# Blacksmith Frame
    if event == "15":
        if st.getQuestItemsCount(2147) >= 1 and st.getQuestItemsCount(1886) >= 1 and st.getQuestItemsCount(1887) >= 5 and st.getQuestItemsCount(1876) >= 10 and st.getQuestItemsCount(57) >= 500:
            st.takeItems(1886,1)
            st.takeItems(1887,5)
            st.takeItems(1876,10)
            st.takeItems(57,500)
            st.giveItems(1892,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Oriharukon 
    if event == "16":
        if st.getQuestItemsCount(1825) >= 1 and st.getQuestItemsCount(1888) >= 1 and st.getQuestItemsCount(1874) >= 4 and st.getQuestItemsCount(1873) >= 12 and st.getQuestItemsCount(57) >= 500:
            st.takeItems(1888,1)
            st.takeItems(1874,4)
            st.takeItems(1873,12)
            st.takeItems(57,500)
            st.giveItems(1893,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Crafted Leather
    if event == "17":
        if st.getQuestItemsCount(2148) >= 1 and st.getQuestItemsCount(1884) >= 4 and st.getQuestItemsCount(1882) >= 4 and st.getQuestItemsCount(1870) >= 4 and st.getQuestItemsCount(57) >= 250:
            st.takeItems(1884,4)
            st.takeItems(1882,4)
            st.takeItems(1870,4)
            st.takeItems(57,250)
            st.giveItems(1894,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Metallic Fiber
    if event == "18":
        if st.getQuestItemsCount(2149) >= 1 and st.getQuestItemsCount(1873) >= 15 and st.getQuestItemsCount(1884) >= 20 and st.getQuestItemsCount(57) >= 500:
            st.takeItems(1873,15)
            st.takeItems(1884,20)
            st.takeItems(57,500)
            st.giveItems(1895,20)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Metallic Thread
    if event == "19":
        if st.getQuestItemsCount(5472) >= 1 and st.getQuestItemsCount(1868) >= 10 and st.getQuestItemsCount(1869) >= 10 and st.getQuestItemsCount(57) >= 250:
            st.takeItems(1868,10)
            st.takeItems(1869,10)
            st.takeItems(57,250)
            st.giveItems(5549,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Metal Hardner
    if event == "20":
        if st.getQuestItemsCount(5231) >= 1 and st.getQuestItemsCount(1869) >= 10 and st.getQuestItemsCount(1865) >= 10 and st.getQuestItemsCount(1864) >= 10 and st.getQuestItemsCount(57) >= 250:
            st.takeItems(1869,10)
            st.takeItems(1865,10)
            st.takeItems(1864,10)
            st.takeItems(57,250)
            st.giveItems(5220,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Reinforced Metal Plate
    if event == "21":
        if st.getQuestItemsCount(5473) >= 1 and st.getQuestItemsCount(5549) >= 5 and st.getQuestItemsCount(1876) >= 5 and st.getQuestItemsCount(57) >= 500:
            st.takeItems(5549,5)
            st.takeItems(1876,5)
            st.takeItems(57,500)
            st.giveItems(5550,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."


# Maestro Anvil Lock
    if event == "22":
        if st.getQuestItemsCount(4123) >= 1 and st.getQuestItemsCount(1888) >= 4 and st.getQuestItemsCount(4039) >= 4 and st.getQuestItemsCount(4040) >= 4 and st.getQuestItemsCount(57) >= 2500:
            st.takeItems(1888,4)
            st.takeItems(4039,4)
            st.takeItems(4040,4)
            st.takeItems(57,2500)
            st.giveItems(4046,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Maestro Holder
    if event == "23":
        if st.getQuestItemsCount(4122) >= 1 and st.getQuestItemsCount(1887) >= 10 and st.getQuestItemsCount(4041) >= 10 and st.getQuestItemsCount(4040) >= 10 and st.getQuestItemsCount(57) >= 2500:
            st.takeItems(1887,10)
            st.takeItems(4041,10)
            st.takeItems(4040,10)
            st.takeItems(57,2500)
            st.giveItems(4045,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Maestro Mold
    if event == "24":
        if st.getQuestItemsCount(4125) >= 1 and st.getQuestItemsCount(1892) >= 1 and st.getQuestItemsCount(4043) >= 5 and st.getQuestItemsCount(4039) >= 10 and st.getQuestItemsCount(57) >= 2500:
            st.takeItems(1892,1)
            st.takeItems(4043,5)
            st.takeItems(4039,10)
            st.takeItems(57,2500)
            st.giveItems(4048,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Craftsman Mold
    if event == "25":
        if st.getQuestItemsCount(4124) >= 1 and st.getQuestItemsCount(1891) >= 2 and st.getQuestItemsCount(4042) >= 5 and st.getQuestItemsCount(4041) >= 20 and st.getQuestItemsCount(57) >= 2500:
            st.takeItems(1891,2)
            st.takeItems(4042,5)
            st.takeItems(4041,20)
            st.takeItems(57,2500)
            st.giveItems(4047,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Leolins Mold
    if event == "26":
        if st.getQuestItemsCount(5549) >= 40 and st.getQuestItemsCount(5550) >= 10 and st.getQuestItemsCount(1877) >= 15 and st.getQuestItemsCount(57) >= 10000:
            st.takeItems(5549,40)
            st.takeItems(5550,10)
            st.takeItems(1877,15)
            st.takeItems(57,10000)
            st.giveItems(5551,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Warsmiths Mold
    if event == "27":
        if st.getQuestItemsCount(1891) >= 1 and st.getQuestItemsCount(4041) >= 10 and st.getQuestItemsCount(4042) >= 5 and st.getQuestItemsCount(57) >= 10000:
            st.takeItems(1891,1)
            st.takeItems(4041,10)
            st.takeItems(4042,5)
            st.takeItems(57,10000)
            st.giveItems(5552,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Archsmiths Anvil Lock
    if event == "28":
        if st.getQuestItemsCount(4046) >= 3 and st.getQuestItemsCount(4040) >= 20 and st.getQuestItemsCount(4044) >= 10 and st.getQuestItemsCount(57) >= 10000:
            st.takeItems(4046,3)
            st.takeItems(4040,20)
            st.takeItems(4044,10)
            st.takeItems(57,10000)
            st.giveItems(5553,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."

# Warsmith Holder
    if event == "29":
        if st.getQuestItemsCount(4045) >= 2 and st.getQuestItemsCount(4039) >= 10 and st.getQuestItemsCount(1868) >= 20 and st.getQuestItemsCount(57) >= 10000:
            st.takeItems(4045,2)
            st.takeItems(4039,10)
            st.takeItems(1868,20)
            st.takeItems(57,10000)
            st.giveItems(5554,1)
            htmltext = "Item has been succesfully created."
        else:
             htmltext = "You do not have enough materials."


    if event == "0":
      htmltext = "Trade has been canceled."
    
    if htmltext != event:
      st.setState(COMPLETED)
      st.exitQuest(1)

    return htmltext

 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say to you.</body></html>"
   st.set("cond","0")
   st.setState(STARTED)
   return "1.htm"

QUEST       = Quest(1004,"1004_create","Blacksmith")
CREATED     = State('Start',     QUEST)
STARTED     = State('Started',   QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

for npcId in SMITHS :
    QUEST.addStartNpc(npcId)
    STARTED.addTalkId(npcId)

print "importing blacksmith data: 1004_create"
