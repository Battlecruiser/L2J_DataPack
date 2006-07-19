# Written by Advi
# v1.2, NewAge: Removed 'Might Mortal' from daggers, no longer available in C3, added rest of weapons, replaced 'Bigsword' with 'Fist'
# v1.3 DrLecter: added adena support; NewAge: added correct prices/crystals
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
from net.sf.l2j.gameserver import ItemTable

SMITHS = [7283,7298,7300,7317,7458,7471,7526,7527,7536,7621,7678,7688,7846,7898,8002,8044,8271,8274,8316,8539,8583,8626,8668]

############################## Feel Free to add more Weapons ##########################################################################################################3

# Weapon enhancement definition  WeaponID:[Icon, [Enhancement, newWeaponID, CrystalID, MaterialID, MaterialQuant, Adena], ...]


EnhanceList={
#Bows
281:["weapon_crystallized_ice_bow_i01", [["Guidance", 4810, 4634, 2131, 97, 291000], ["Evasion", 4811, 4645, 2131, 97, 291000], ["Quick Recovery", 4812, 4656, 2131, 97, 291000]]], 
285:["weapon_noble_elven_bow_i01", [["Evasion", 4816, 4635, 2131, 238, 714000], ["Miser", 4817, 4646, 2131, 238, 714000], ["Cheap Shot", 4818, 4657, 2131, 238, 714000]]], 
283:["weapon_akat_long_bow_i01", [["Guidance", 4819, 4636, 2131, 306, 918000], ["Evasion", 4820, 4647, 2131, 306, 918000], ["Miser", 4821, 4658, 2131, 306, 918000]]], 
286:["weapon_eminence_bow_i01", [["Guidance", 4822, 4637, 2131, 555, 1665000], ["Miser", 4823, 4648, 2131, 555, 1665000], ["Cheap Shot", 4824, 4659, 2131, 555, 1665000]]], 
284:["weapon_dark_elven_long_bow_i01", [["Evasion", 4825, 4638, 2132, 222, 2220000], ["Critical Bleed", 4826, 4649, 2132, 222, 2220000], ["Miser", 4827, 4660, 2132, 222, 2220000]]], 
287:["weapon_hazard_bow_i01", [["Guidance", 4828, 4639, 2132, 339, 3390000], ["Quick Recovery", 4829, 4650, 2132, 339, 3390000], ["Cheap Shot", 4830, 4661, 2132, 339, 3390000]]], 
282:["weapon_elemental_bow_i01", [["Guidance", 4813, 4635, 2131, 238, 714000], ["Miser", 4814, 4646, 2131, 238, 714000], ["Quick Recovery", 4815, 4657, 2131, 238, 714000]]], 
# Swords
72: ["weapon_stormbringer_i01", [["Critical Anger", 4681, 4634, 2131, 97, 291000], ["Focus", 4682, 4645, 2131, 97, 291000], ["Light", 4683, 4656, 2131, 97, 291000]]], 
73: ["weapon_shamshir_i01", [["Guidance", 4684, 4635, 2131, 238, 714000], ["Back Blow", 4685, 4646, 2131, 238, 714000], ["Rsk. Evasion", 4686, 4657, 2131, 238, 714000]]], 
74: ["weapon_katana_i01", [["Focus", 4687, 4635, 2131, 238, 714000], ["Critical Damage", 4688, 4646, 2131, 238, 714000], ["Haste", 4689, 4657, 2131, 238, 714000]]], 
131:["weapon_spirits_sword_i01", [["Critical Damage", 4690, 4635, 2131, 238, 714000], ["Critical Poison", 4691, 4646, 2131, 238, 714000], ["Haste", 4692, 4657, 2131, 238, 714000]]], 
133:["weapon_raid_sword_i01", [["Focus", 4693, 4635, 2131, 238, 714000], ["Critical Drain", 4694, 4646, 2131, 238, 714000], ["Critical Poison", 4695, 4657, 2131, 238, 714000]]], 
76: ["weapon_sword_of_delusion_i01", [["Focus", 4699, 4636, 2131, 306, 918000], ["Health", 4700, 4647, 2131, 306, 918000], ["Rsk. Haste", 4701, 4658, 2131, 306, 918000]]], 
77: ["weapon_tsurugi_i01", [["Focus", 4702, 4636, 2131, 306, 918000], ["Critical Damage", 4703, 4647, 2131, 306, 918000], ["Haste", 4704, 4658, 2131, 306, 918000]]], 
134:["weapon_sword_of_nightmare_i01", [["Health", 4705, 4636, 2131, 306, 918000], ["Focus", 4706, 4647, 2131, 306, 918000], ["Light", 4707, 4658, 2131, 306, 918000]]], 
142:["weapon_keshanberk_i01", [["Guidance", 4714, 4638, 2132, 222, 2220000], ["Focus", 4715, 4649, 2132, 222, 2220000], ["Back Blow", 4716, 4660, 2132, 222, 2220000]]], 
148:["weapon_sword_of_valhalla_i01", [["Acumen", 7722, 4638, 2132, 222, 2220000], ["Magic Weakness", 7723, 4649, 2132, 222, 2220000], ["Magic Regeneration", 7724, 4660, 2132, 222, 2220000]]], 
79: ["weapon_sword_of_damascus_i01", [["Focus", 4717, 4639, 2132, 339, 3390000], ["Critical Damage", 4718, 4650, 2132, 339, 3390000], ["Haste", 4719, 4661, 2132, 339, 3390000]]], 
78: ["weapon_great_sword_i01", [["Health", 4723, 4638, 2132, 222, 2220000], ["Critical Damage", 4724, 4649, 2132, 222, 2220000], ["Focus", 4725, 4660, 2132, 222, 2220000]]], 
132:["weapon_sword_of_limit_i01", [["Guidance", 6307, 4636, 2131, 306, 918000], ["Critical Drain", 6308, 4647, 2131, 306, 918000], ["Health", 6309, 4658, 2131, 306, 918000]]], 
145:["weapon_deathbreath_sword_i01", [["Empower", 6310, 4636, 2131, 306, 918000], ["Magic Power", 6311, 4647, 2131, 306, 918000], ["Magic Silence", 6312, 4658, 2131, 306, 918000]]], 
84:["weapon_homunkuluss_sword_i01", [["Acumen", 6313, 4636, 2131, 306, 918000], ["Conversion", 6314, 4647, 2131, 306, 918000], ["Magic Paralyze", 6315, 4658, 2131, 306, 918000]]], 
75:["weapon_caliburs_i01", [["Guidance", 4696, 4636, 2131, 306, 918000], ["Focus", 4697, 4647, 2131, 306, 918000], ["Critical Damage", 4698, 4658, 2131, 306, 918000]]], 
135:["weapon_samurai_longsword_i01", [["Focus", 4708, 4637, 2131, 555, 1665000], ["Critical Damage", 4709, 4648, 2131, 555, 1665000], ["Haste", 4710, 4659, 2131, 555, 1665000]]], 
71:["weapon_flamberge_i01", [["Critical Damage", 4711, 4634, 2131, 97, 291000], ["Focus", 4712, 4645, 2131, 97, 291000], ["Light", 4713, 4656, 2131, 97, 291000]]], 
5286:["weapon_berserker_blade_i01", [["Focus", 6347, 4637, 2131, 555, 1665000], ["Critical Damage", 6348, 4648, 2131, 555, 1665000], ["Haste", 6349, 4659, 2131, 555, 1665000]]], 
# Blunts
89:["weapon_big_hammer_i01", [["Health", 4726, 4634, 2131, 97, 291000], ["Rsk. Focus", 4727, 4645, 2131, 97, 291000], ["Haste", 4728, 4656, 2131, 97, 291000]]], 
160:["weapon_battle_axe_i01", [["Anger", 4729, 4634, 2131, 97, 291000], ["Rsk. Focus", 4730, 4645, 2131, 97, 291000], ["Haste", 4731, 4656, 2131, 97, 291000]]], 
161:["weapon_war_pick_i01", [["Anger", 4732, 4634, 2131, 97, 291000], ["Rsk. Focus", 4733, 4645, 2131, 97, 291000], ["Haste", 4734, 4656, 2131, 97, 291000]]], 
193:["weapon_stick_of_faith_i01", [["Mana Up", 7701, 4634, 2131, 97, 291000], ["Magic Hold", 7702, 4645, 2131, 97, 291000], ["Magic Shield", 7703, 4656, 2131, 97, 291000]]], 
162:["weapon_war_axe_i01", [["Anger", 4741, 4636, 2131, 306, 918000], ["Health", 4742, 4647, 2131, 306, 918000], ["Haste", 4743, 4658, 2131, 306, 918000]]], 
173:["weapon_skull_graver_i01", [["Anger", 4735, 4634, 2131, 97, 291000], ["Health", 4736, 4645, 2131, 97, 291000], ["Rsk. Focus", 4737, 4656, 2131, 97, 291000]]], 
2502:["weapon_dwarven_warhammer_i01", [["Anger", 4738, 4635, 2131, 238, 714000], ["Health", 4739, 4646, 2131, 238, 714000], ["Haste", 4740, 4657, 2131, 238, 714000]]], 
2503:["weapon_yaksa_mace_i01", [["Anger", 4744, 4637, 2131, 555, 1665000], ["Health", 4745, 4648, 2131, 555, 1665000], ["Rsk. Focus", 4746, 4659, 2131, 555, 1665000]]], 
91: ["weapon_heavy_war_axe_i01", [["Anger", 4747, 4638, 2132, 222, 2220000], ["Health", 4748, 4649, 2132, 222, 2220000], ["Rsk. Focus", 4749, 4660, 2132, 222, 2220000]]], 
171:["weapon_deadmans_glory_i01", [["Anger", 4750, 4639, 2132, 339, 3390000], ["Health", 4751, 4650, 2132, 339, 3390000], ["Haste", 4752, 4661, 2132, 339, 3390000]]], 
175:["weapon_art_of_battle_axe_i01", [["Health", 4753, 4639, 2132, 339, 3390000], ["Rsk. Focus", 4754, 4650, 2132, 339, 3390000], ["Haste", 4755, 4661, 2132, 339, 3390000]]], 
192:["weapon_crystal_staff_i01", [["Rsk. Evasion", 4867, 4634, 2131, 97, 291000], ["Mana Up", 4868, 4645, 2131, 97, 291000], ["Bodily Blessing", 4869, 4656, 2131, 97, 291000]]], 
195:["weapon_cursed_staff_i01", [["Magic Hold", 4873, 4635, 2131, 238, 714000], ["Magic Poison", 4874, 4646, 2131, 238, 714000], ["Magic Weakness", 4875, 4657, 2131, 238, 714000]]], 
174:["weapon_nirvana_axe_i01", [["Magic Power", 7707, 4636, 2131, 306, 918000], ["Magic Poison", 7708, 4647, 2131, 306, 918000], ["Magic Weakness", 7709, 4658, 2131, 306, 918000]]], 
196:["weapon_stick_of_eternity_i01", [["Magic Empower", 7704, 4636, 2131, 306, 918000], ["Rsk. Evasion", 7705, 4647, 2131, 306, 918000], ["Bless The Body", 7706, 4658, 2131, 306, 918000]]],
197:["weapon_paradia_staff_i01", [["Magic Regeneration", 4876, 4636, 2131, 306, 918000], ["Mental Shield", 4877, 4647, 2131, 306, 918000], ["Magic Hold", 4878, 4658, 2131, 306, 918000]]], 
198:["weapon_inferno_staff_i01", [["Acumen", 7716, 4636, 2131, 306, 918000], ["Magic Silence", 7717, 4647, 2131, 306, 918000], ["Magic Paralyze", 7718, 4658, 2131, 306, 918000]]], 
200:["weapon_sages_staff_i01", [["Magic Hold", 4882, 4636, 2131, 306, 918000], ["Magic Poison", 4883, 4647, 2131, 306, 918000], ["Magic Weakness", 4884, 4658, 2131, 306, 918000]]], 
201:["weapon_club_of_nature_i01", [["Acumen", 7710, 4636, 2131, 306, 918000], ["Mental Shield", 7711, 4647, 2131, 306, 918000], ["Magic Hold", 7712, 4658, 2131, 306, 918000]]], 
202:["weapon_mace_of_underworld_i01", [["Mana Up", 7713, 4636, 2131, 306, 918000], ["Magic Silence", 7714, 4647, 2131, 306, 918000], ["Conversion", 7715, 4658, 2131, 306, 918000]]], 
203:["weapon_paagrio_axe_i01", [["Mana Up", 4885, 4636, 2131, 225, 675000], ["Magic Weakness", 4886, 4647, 2131, 225, 675000], ["Magic Chaos", 4887, 4658, 2131, 225, 675000]]], 
204:["weapon_deadmans_staff_i01", [["Magic Regeneration", 4888, 4637, 2131, 555, 1665000], ["Mental Shield", 4889, 4648, 2131, 555, 1665000], ["Magic Hold", 4890, 4659, 2131, 555, 1665000]]], 
205:["weapon_ghouls_staff_i01", [["Rsk. Evasion", 4891, 4637, 2131, 555, 1665000], ["Mana Up", 4892, 4648, 2131, 555, 1665000], ["Bodily Blessing", 4893, 4659, 2131, 555, 1665000]]], 
206:["weapon_demons_staff_i01", [["Magic Poison", 4894, 4637, 2131, 555, 1665000], ["Magic Weakness", 4895, 4648, 2131, 555, 1665000], ["Magic Chaos", 4896, 4659, 2131, 555, 1665000]]], 
92: ["weapon_sprites_staff_i01", [["Magic Regeneration", 4897, 4638, 2132, 222, 2220000], ["Mental Shield", 4898, 4649, 2132, 222, 2220000], ["Magic Hold", 4899, 4660, 2132, 222, 2220000]]], 
210:["weapon_staff_of_evil_spirit_magic_i01", [["Magic Focus", 4900, 4639, 2132, 339, 3390000], ["Bodily Blessing", 4901, 4650, 2132, 339, 3390000], ["Magic Poison", 4902, 4661, 2132, 339, 3390000]]], 
191:["weapon_heavy_doom_hammer_i01", [["Magic Regeneration", 4864, 4634, 2131, 97, 291000], ["Mental Shield", 4865, 4645, 2131, 97, 291000], ["Magic Hold", 4866, 4656, 2131, 97, 291000]]], 
194:["weapon_heavy_doom_axe_i01", [["Magic Poison", 4870, 4634, 2131, 97, 291000], ["Magic Weakness", 4871, 4645, 2131, 97, 291000], ["Magic Chaos", 4872, 4656, 2131, 97, 291000]]], 
199:["weapon_paagrio_hammer_i01", [["Rsk. Evasion", 4879, 4636, 2131, 306, 918000], ["Magic Poison", 4880, 4647, 2131, 306, 918000], ["Magic Weakness", 4881, 4658, 2131, 306, 918000]]], 
# Dagger'
231:["weapon_grace_dagger_i01", [["Evasion", 4768, 4636, 2131, 306, 918000], ["Focus", 4769, 4647, 2131, 306, 918000], ["Back Blow", 4770, 4658, 2131, 306, 918000]]], 
233:["weapon_dark_screamer_i01", [["Evasion", 4771, 4636, 2131, 306, 918000], ["Focus", 4772, 4647, 2131, 306, 918000], ["Critical Bleed", 4773, 4658, 2131, 306, 918000]]], 
228:["weapon_crystal_dagger_i01", [["Critical Bleed", 4774, 4637, 2131, 555, 1665000], ["Critical Poison", 4775, 4648, 2131, 555, 1665000], ["Critical Damage", 6358, 4659, 2131, 555, 1665000]]], 
229:["weapon_kris_i01", [["Evasion", 4777, 4638, 2132, 222, 2220000], ["Focus", 4778, 4649, 2132, 222, 2220000], ["Back Blow", 4779, 4660, 2132, 222, 2220000]]], 
243:["weapon_hell_knife_i01", [["Magic Regeneration", 7813, 4638, 2132, 222, 2220000], ["Mental Shield", 7814, 4649, 2132, 222, 2220000], ["Magic Weakness", 7815, 4660, 2132, 222, 2220000]]], 
234:["weapon_demons_sword_i01", [["Critical Bleed", 4780, 4639, 2132, 339, 3390000], ["Critical Poison", 4781, 4650, 2132, 339, 3390000], ["Critical Damage", 6359, 4661, 2132, 339, 3390000]]], 
226:["weapon_cursed_dagger_i01", [["Critical Bleed", 4759, 4634, 2131, 97, 291000], ["Critical Poison", 4760, 4645, 2131, 97, 291000], ["Rsk. Haste", 4761, 4656, 2131, 97, 291000]]], 
232:["weapon_darkelven_dagger_i01", [["Focus", 4762, 4634, 2131, 97, 291000], ["Back Blow", 4763, 4645, 2131, 97, 291000], ["Rsk. Haste", 6356, 4656, 2131, 97, 291000]]], 
227:["weapon_stiletto_i01", [["Critical Bleed", 4765, 4635, 2131, 238, 714000], ["Critical Poison", 4766, 4646, 2131, 238, 714000], ["Rsk. Haste", 6357, 4657, 2131, 238, 714000]]], 
242:["weapon_dagger_of_magicflame_i01", [["Mana Up", 7810, 4635, 2131, 238, 714000], ["Magic Hold", 7811, 4646, 2131, 238, 714000], ["Magic Silence", 7812, 4657, 2131, 238, 714000]]], 
# Poleaxe'
301:["weapon_scorpion_i01", [["Anger", 4846, 4636, 2131, 225, 675000], ["Critical Stun", 4847, 4647, 2131, 225, 675000], ["Long Blow", 4848, 4659, 2131, 225, 675000]]], 
303:["weapon_widow_maker_i01", [["Critical Stun", 4849, 4636, 2131, 225, 675000], ["Long Blow", 4850, 4647, 2131, 225, 675000], ["Wide Blow", 4851, 4658, 2131, 225, 675000]]], 
299:["weapon_orcish_poleaxe_i01", [["Critical Stun", 4852, 4637, 2131, 555, 1665000], ["Long Blow", 4853, 4648, 2131, 555, 1665000], ["Wide Blow", 4854, 4659, 2131, 555, 1665000]]], 
300:["weapon_great_axe_i01", [["Anger", 4855, 4638, 2132, 222, 2220000], ["Critical Stun", 4856, 4649, 2132, 222, 2220000], ["Light", 4857, 4660, 2132, 222, 2220000]]], 
97: ["weapon_lance_i01", [["Anger", 4858, 4639, 2132, 339, 3390000], ["Critical Stun", 4859, 4650, 2132, 339, 3390000], ["Long Blow", 4860, 4661, 2132, 339, 3390000]]], 
96:["weapon_scythe_i01", [["Anger", 4834, 4634, 2131, 97, 291000], ["Critical Stun", 4835, 4645, 2131, 97, 291000], ["Light", 4836, 4656, 2131, 97, 291000]]], 
298:["weapon_orcish_glaive_i01", [["Anger", 4837, 4634, 2131, 97, 291000], ["Critical Stun", 4838, 4645, 2131, 97, 291000], ["Long Blow", 4839, 4656, 2131, 97, 291000]]],
302:["weapon_body_slasher_i01", [["Critical Stun", 4840, 4634, 2131, 97, 291000], ["Long Blow", 4841, 4645, 2131, 97, 291000], ["Wide Blow", 4842, 4656, 2131, 97, 291000]]], 
94:["weapon_bech_de_corbin_i01", [["Critical Stun", 4843, 4635, 2131, 238, 714000], ["Long Blow", 4844, 4646, 2131, 238, 714000], ["Light", 4845, 4657, 2131, 238, 714000]]], 
95:["weapon_poleaxe_i01",[["Critical Stun", 7719, 4636, 2131, 306, 918000], ["Long Blow", 7720, 4647, 2131, 306, 918000], ["Wide Blow", 7721, 4658, 2131, 306, 918000]]],
# Fist'
263:["weapon_chakram_i01", [["Critical Drain", 4789, 4634, 2131, 97, 291000], ["Critical Poison", 4790, 4645, 2131, 97, 291000], ["Rsk. Haste", 4791, 4656, 2131, 97, 291000]]], 
265:["weapon_fist_blade_i01", [["Rsk. Evasion", 4792, 4636, 2131, 306, 918000], ["Rsk. Haste", 4793, 4647, 2131, 306, 918000], ["Haste", 4794, 4658, 2131, 306, 918000]]], 
266:["weapon_great_pata_i01", [["Critical Drain", 4795, 4637, 2131, 555, 1665000], ["Critical Poison", 4796, 4648, 2131, 555, 1665000], ["Rsk. Haste", 4797, 4659, 2131, 555, 1665000]]], 
267:["weapon_arthro_nail_i01", [["Critical Poison", 4801, 4638, 2132, 222, 2220000], ["Rsk. Evasion", 4802, 4649, 2132, 222, 2220000], ["Rsk. Haste", 4803, 4660, 2132, 222, 2220000]]], 
268:["weapon_bellion_cestus_i01", [["Critical Drain", 4804, 4639, 2132, 339, 3390000], ["Critical Poison", 4805, 4650, 2132, 339, 3390000], ["Rsk. Haste", 4806, 4661, 2132, 339, 3390000]]], 
4233:["weapon_knuckle_dust_i01", [["Rsk. Evasion", 4798, 4635, 2131, 238, 714000], ["Rsk. Haste", 4799, 4646, 2131, 238, 714000], ["Haste", 4800, 4657, 2131, 238, 714000]]], 
}


############################################################## DO NOT MODIFY BELOW THIS LINE #####################################################################################


def getItemName(Item):
    ItemName = Item.getItem().getName()
    if Item.getEnchantLevel() > 0: 
        ItemName = "+" + str(Item.getEnchantLevel()) + " " + ItemName
    return ItemName


def getMaterialName(MaterialID):
    if MaterialID in range(2131, 2134):
        return "Gemstone " + chr(ord('A') + 2133 - MaterialID)
    if MaterialID in range(4629, 4640):
        return "Red Soul Crystal - Stage " + str(MaterialID - 4629)
    if MaterialID in range(4640, 4651):
        return "Green Soul Crystal - Stage " + str(MaterialID - 4640)
    if MaterialID in range(4651, 4662):
        return "Blue Soul Crystal - Stage " + str(MaterialID - 4651)
    return "Unknown material"
  
    
def getMaterialIcon(MaterialID):
    if MaterialID in range(2131, 2134):
        return "etc_crystal_ball_green_i00"
    if MaterialID in range(4629, 4640):
        return "etc_crystal_red_i00"
    if MaterialID in range(4640, 4651):
        return "etc_crystal_green_i00"
    if MaterialID in range(4651, 4662):
        return "etc_crystal_blue_i00"
    return "etc_unknown_material_i00"


# Main Code
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    
    # Creates a List to chose a weapon
    if event == "1":
        htmltext = ""
        for Item in st.getPlayer().getInventory().getItems():
            if Item.getItemId() in EnhanceList and not Item.isEquipped():
                Icon, Enhancements = EnhanceList[Item.getItemId()]
                EnhancID = 0
                for Name, WeaponID, CrystalID, MaterialID, MaterialQuant,Adena in Enhancements:
                    htmltext += "<tr>\n<td width=35><img src=\"icon." + Icon + "\" width=32 height=32 align=\"left\"></td>\n" \
                        "<td width=835><table border=0 width=\"835\">\n<tr><td><a action=\"bypass -h Quest 1007_enhance 2_" + str(Item.getObjectId()) + "." + str(EnhancID) + "\">" + getItemName(Item) + ": " + Name + "</a></td></tr>\n" \
                        "<tr><td><font color=\"B09878\">Enhance</font></td></tr></table></td>\n</tr>"
                    EnhancID += 1
        if htmltext == "": 
            htmltext = "<tr><td>You have no enhancable weapon in your inventory</td></tr>"
        htmltext = "<html><body>\nList:\n<left>\n<table width=870 border=0>\n" + htmltext + "</table>\n<br></left></body></html>"
        return htmltext

    # shows you how much materials you need to enhance, ok button to go forward, too
    elif event.startswith("2_"):
        reqEnh = event.replace("2_", "").split(".")
        ObjectID = int(reqEnh[0])
        EnhancID = int(reqEnh[1])
        Item = st.getPlayer().getInventory().getItemByObjectId(ObjectID)
        if Item.getItemId() in EnhanceList:
            Icon, Enhancements = EnhanceList[Item.getItemId()]
            Name, WeaponID, CrystalID, MaterialID, MaterialQuant, Adena = Enhancements[EnhancID]
            htmltext = st.showHtmlFile("2.htm")
            return htmltext.replace("<WeaponName>", getItemName(Item) + ": " + Name)\
                .replace("<WeaponIcon>", Icon)\
                .replace("<CrystalName>", getMaterialName(CrystalID))\
                .replace("<CrystalIcon>", getMaterialIcon(CrystalID))\
                .replace("<MaterialName>", getMaterialName(MaterialID))\
                .replace("<MaterialIcon>", getMaterialIcon(MaterialID))\
                .replace("<MaterialQuantity>", str(MaterialQuant))\
                .replace("<Adena>", str(Adena))\
                .replace("<EventOK>", "3_" + str(Item.getObjectId()) + "." + str(EnhancID))
    
    # this handels the whole enhance stuff with objectIds, not ItemIds... no html shows up.. just work and socket return
    elif event.startswith("3_"):
        reqEnh = event.replace("3_", "").split(".")
        ObjectID = int(reqEnh[0])
        EnhancID = int(reqEnh[1])
        Item = st.getPlayer().getInventory().getItemByObjectId(ObjectID)
        if Item.getItemId() in EnhanceList:
            Icon, Enhancements = EnhanceList[Item.getItemId()]
            Name, WeaponID, CrystalID, MaterialID, MaterialQuant, Adena = Enhancements[EnhancID]

            if st.getQuestItemsCount(CrystalID) >= 1 and st.getQuestItemsCount(MaterialID) >= MaterialQuant and st.getQuestItemsCount(57) >= Adena :
                EnchantLevel = Item.getEnchantLevel()
                st.getPlayer().destroyItem("enhance_1007", ObjectID, 1, st.getPlayer(), 0)
                NewItem = ItemTable.getInstance().createItem("enhance", WeaponID, 1, st.getPlayer())
                NewItem.setEnchantLevel(EnchantLevel)
                st.getPlayer().addItem("enhance", NewItem, st.getPlayer(), 0)
                st.takeItems(CrystalID, 1)
                st.takeItems(MaterialID, MaterialQuant)
                st.takeItems(57,Adena)
                htmltext = "Item has been succesfully enhanced!"
            else :
                htmltext = "You do not have enough materials."
    
    # if event is 0, or has a bug... trade is canceled
    else :
        htmltext = "Trade has been cancelled."

    
    st.setState(COMPLETED)
    st.exitQuest(1)
    return htmltext
    
    
# this just return new html, if the player can talk with this npc about that enhance stuff
 def onTalk (self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say to you.</body></html>"
   st.set("cond","0")
   st.setState(STARTED)
   return "1.htm"


QUEST       = Quest(1007,"1007_enhance","Blacksmith")
CREATED     = State('Start',     QUEST)
STARTED     = State('Started',   QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)


# init all npc to the correct stats
for npcId in SMITHS:
	QUEST.addStartNpc(npcId)
	STARTED.addTalkId(npcId)
	
# always at the end, then it shows only up if anything is correct in the code.. no jython error.. because we cant check jython errors with idle
print "importing blacksmith data: 1007_enhance"
