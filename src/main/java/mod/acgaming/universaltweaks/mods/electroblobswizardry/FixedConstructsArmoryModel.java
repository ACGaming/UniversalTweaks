package mod.acgaming.universaltweaks.mods.electroblobswizardry;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.inventory.EntityEquipmentSlot;

import c4.conarm.client.models.ModelConstructsArmor;

public class FixedConstructsArmoryModel extends ModelConstructsArmor
{
    public ModelRenderer pantsAnchor;
    public ModelRenderer belt;
    public ModelRenderer skirtLeft;
    public ModelRenderer skirtRight;
    public ModelRenderer skirtFront;
    public ModelRenderer skirtBack;

    public ModelRenderer legLeftAnchor;
    public ModelRenderer legRightAnchor;
    public ModelRenderer legLeft;
    public ModelRenderer legRight;

    public ModelRenderer bootLeftAnchor;
    public ModelRenderer bootRightAnchor;
    public ModelRenderer bootLeft;
    public ModelRenderer bootRight;
    public ModelRenderer bootPlateLeft;
    public ModelRenderer bootPlateRight;

    public ModelRenderer chestAnchor;
    public ModelRenderer chestBottom;
    public ModelRenderer chestTop;
    public ModelRenderer chestFront;
    public ModelRenderer chestBack;

    public ModelRenderer armLeftAnchor;
    public ModelRenderer armRightAnchor;
    public ModelRenderer shoulderLeft;
    public ModelRenderer shoulderLeftEx;
    public ModelRenderer gauntletLeft;
    public ModelRenderer shoulderRight;
    public ModelRenderer shoulderRightEx;
    public ModelRenderer gauntletRight;

    public ModelRenderer headAnchor;
    public ModelRenderer helmet;
    public ModelRenderer face;

    public FixedConstructsArmoryModel(EntityEquipmentSlot slot)
    {
        super(slot);

        this.textureWidth = 64;
        this.textureHeight = 64;

        float scale = 0.6F;

        //Helmet
        this.headAnchor = new ModelRenderer(this, 0, 0);

        this.face = new ModelRenderer(this, 3, 10);
        this.face.setRotationPoint(0.0F, 0.0F, -1.5F);
        this.face.addBox(-4.0F, -8.0F, -2.5F, 8, 8, 5, scale + 0.7F);

        this.helmet = new ModelRenderer(this, 32, 48);
        this.helmet.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.helmet.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale + 0.4F);

        //Chestplate
        this.chestAnchor = new ModelRenderer(this, 0, 0);

        this.chestBack = new ModelRenderer(this, 50, 13);
        this.chestBack.setRotationPoint(0.0F, 6.1F, 2.3F);
        this.chestBack.addBox(-3.0F, 0.0F, -0.5F, 6, 3, 1, scale + 0.1F);

        this.chestTop = new ModelRenderer(this, 0, 23);
        this.chestTop.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.chestTop.addBox(-5.0F, 0.0F, -3.0F, 10, 6, 6, scale + 0.1F);

        this.chestFront = new ModelRenderer(this, 50, 13);
        this.chestFront.setRotationPoint(0.0F, 6.1F, -2.3F);
        this.chestFront.addBox(-3.0F, 0.0F, -0.5F, 6, 3, 1, scale + 0.1F);

        this.chestBottom = new ModelRenderer(this, 0, 55);
        this.chestBottom.setRotationPoint(0.0F, 5.0F, 0.0F);
        this.chestBottom.addBox(-4.0F, 0.0F, -2.0F, 8, 5, 4, scale + 0.1F);

        this.armLeftAnchor = new ModelRenderer(this, 0, 0);
        this.armLeftAnchor.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.armRightAnchor = new ModelRenderer(this, 0, 0);
        this.armRightAnchor.setRotationPoint(5.0F, 2.0F, 0.0F);

        this.gauntletLeft = new ModelRenderer(this, 36, 0);
        this.gauntletLeft.setRotationPoint(0.0F, 7.50F, 0.0F);
        this.gauntletLeft.addBox(-1.0F, -2.0F, -2.5F, 4, 5, 5, scale + 0.2F);

        this.gauntletRight = new ModelRenderer(this, 36, 0);
        this.gauntletRight.mirror = true;
        this.gauntletRight.setRotationPoint(0.0F, 7.50F, 0.0F);
        this.gauntletRight.addBox(-3.0F, -2.0F, -2.5F, 4, 5, 5, scale + 0.2F);

        this.shoulderLeft = new ModelRenderer(this, 44, 28);
        this.shoulderLeft.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.shoulderLeft.addBox(-1.0F, -2.0F, -2.5F, 5, 4, 5, scale + 0.2F);
        this.setRotateAngle(shoulderLeft, 0.0F, 0.0F, -0.17453292519943295F);

        this.shoulderRight = new ModelRenderer(this, 44, 28);
        this.shoulderRight.mirror = true;
        this.shoulderRight.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.shoulderRight.addBox(-4.0F, -2.0F, -2.5F, 5, 4, 5, scale + 0.2F);
        this.setRotateAngle(shoulderRight, 0.0F, 0.0F, 0.17453292519943295F);

        this.shoulderRightEx = new ModelRenderer(this, 52, 20);
        this.shoulderRightEx.mirror = true;
        this.shoulderRightEx.setRotationPoint(-2.6F, -1.5F, 0.0F);
        this.shoulderRightEx.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 4, scale + 0.2F);
        this.setRotateAngle(shoulderRightEx, 0.0F, 0.0F, 0.08726646259971647F);

        this.shoulderLeftEx = new ModelRenderer(this, 52, 20);
        this.shoulderLeftEx.setRotationPoint(2.6F, -1.5F, 0.0F);
        this.shoulderLeftEx.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 4, scale + 0.2F);
        this.setRotateAngle(shoulderLeftEx, 0.0F, 0.0F, -0.08726646259971647F);

        //Leggings
        this.pantsAnchor = new ModelRenderer(this, 0, 0);

        this.belt = new ModelRenderer(this, 0, 0);
        this.belt.setRotationPoint(0.0F, 9.5F, 0.0F);
        this.belt.addBox(-4.5F, 0.0F, -2.5F, 9, 2, 5, scale - 0.2F);

        this.skirtFront = new ModelRenderer(this, 54, 7);
        this.skirtFront.setRotationPoint(0.0F, 11.5F, -2.5F);
        this.skirtFront.addBox(-2.0F, 0.0F, -0.5F, 4, 5, 1, scale + 0.0F);
        this.setRotateAngle(skirtFront, -0.08726646259971647F, 0.0F, 0.0F);

        this.skirtRight = new ModelRenderer(this, 36, 10);
        this.skirtRight.mirror = true;
        this.skirtRight.setRotationPoint(-4.5F, 11.5F, 0.0F);
        this.skirtRight.addBox(-0.5F, 0.0F, -2.5F, 2, 6, 5, scale + 0.0F);
        this.setRotateAngle(skirtRight, 0.0F, 0.0F, 0.20943951023931953F);

        this.skirtLeft = new ModelRenderer(this, 36, 10);
        this.skirtLeft.setRotationPoint(4.5F, 11.5F, 0.0F);
        this.skirtLeft.addBox(-1.5F, 0.0F, -2.5F, 2, 6, 5, scale + 0.0F);
        this.setRotateAngle(skirtLeft, 0.0F, 0.0F, -0.20943951023931953F);

        this.skirtBack = new ModelRenderer(this, 54, 0);
        this.skirtBack.setRotationPoint(0.0F, 11.5F, 2.5F);
        this.skirtBack.addBox(-2.0F, 0.0F, -0.5F, 4, 6, 1, scale + 0.0F);
        this.setRotateAngle(skirtBack, 0.08726646259971647F, 0.0F, 0.0F);

        this.legLeftAnchor = new ModelRenderer(this, 0, 0);
        this.legRightAnchor = new ModelRenderer(this, 0, 0);

        this.legRight = new ModelRenderer(this, 0, 40);
        this.legRight.mirror = true;
        this.legRight.setRotationPoint(-1.9F, 0.0F, 0.0F);
        this.legRight.addBox(-2.0F, 0.0F, -2.0F, 4, 9, 4, scale + 0.1F);

        this.legLeft = new ModelRenderer(this, 0, 40);
        this.legLeft.setRotationPoint(1.9F, 0.0F, 0.0F);
        this.legLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 9, 4, scale + 0.1F);

        //Boots
        this.bootLeftAnchor = new ModelRenderer(this, 0, 0);
        this.bootRightAnchor = new ModelRenderer(this, 0, 0);

        this.bootPlateRight = new ModelRenderer(this, 38, 21);
        this.bootPlateRight.mirror = true;
        this.bootPlateRight.setRotationPoint(-2.0F, 5.1F, -2.0F);
        this.bootPlateRight.addBox(-2.5F, 0.0F, -1.0F, 5, 5, 2, scale + 0.0F);

        this.bootPlateLeft = new ModelRenderer(this, 38, 21);
        this.bootPlateLeft.setRotationPoint(2.0F, 5.1F, -2.0F);
        this.bootPlateLeft.addBox(-2.5F, 0.0F, -1.0F, 5, 5, 2, scale + 0.0F);

        this.bootLeft = new ModelRenderer(this, 16, 43);
        this.bootLeft.setRotationPoint(1.9F, 6.0F, 0.0F);
        this.bootLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, scale + 0.4F);

        this.bootRight = new ModelRenderer(this, 16, 43);
        this.bootRight.mirror = true;
        this.bootRight.setRotationPoint(-1.9F, 6.0F, 0.0F);
        this.bootRight.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, scale + 0.4F);

        //Hierarchy
        this.headAnchor.addChild(this.helmet);
        this.headAnchor.addChild(this.face);

        this.chestAnchor.addChild(this.chestTop);
        this.chestTop.addChild(this.chestBack);
        this.chestTop.addChild(this.chestBottom);
        this.chestTop.addChild(this.chestFront);
        this.armLeftAnchor.addChild(this.shoulderLeft);
        this.armLeftAnchor.addChild(this.shoulderLeftEx);
        this.armLeftAnchor.addChild(this.gauntletLeft);
        this.armRightAnchor.addChild(this.shoulderRight);
        this.armRightAnchor.addChild(this.shoulderRightEx);
        this.armRightAnchor.addChild(this.gauntletRight);

        this.pantsAnchor.addChild(this.belt);
        this.pantsAnchor.addChild(this.skirtBack);
        this.pantsAnchor.addChild(this.skirtFront);
        this.pantsAnchor.addChild(this.skirtLeft);
        this.pantsAnchor.addChild(this.skirtRight);

        this.legLeftAnchor.addChild(this.legLeft);
        this.legRightAnchor.addChild(this.legRight);

        this.bootLeftAnchor.addChild(this.bootPlateLeft);
        this.bootLeftAnchor.addChild(this.bootLeft);
        this.bootRightAnchor.addChild(this.bootPlateRight);
        this.bootRightAnchor.addChild(this.bootRight);

        headAnchor.showModel = slot == EntityEquipmentSlot.HEAD;
        chestAnchor.showModel = slot == EntityEquipmentSlot.CHEST;
        armRightAnchor.showModel = slot == EntityEquipmentSlot.CHEST;
        armLeftAnchor.showModel = slot == EntityEquipmentSlot.CHEST;
        pantsAnchor.showModel = slot == EntityEquipmentSlot.LEGS;
        legLeftAnchor.showModel = slot == EntityEquipmentSlot.LEGS;
        legRightAnchor.showModel = slot == EntityEquipmentSlot.LEGS;
        bootLeftAnchor.showModel = slot == EntityEquipmentSlot.FEET;
        bootRightAnchor.showModel = slot == EntityEquipmentSlot.FEET;
        bipedHeadwear.showModel = false;

        bipedHead = headAnchor;
        bipedBody = chestAnchor;
        bipedRightArm = armRightAnchor;
        bipedLeftArm = armLeftAnchor;

        if (slot == EntityEquipmentSlot.LEGS)
        {
            bipedBody = pantsAnchor;
            bipedLeftLeg = legLeftAnchor;
            bipedRightLeg = legRightAnchor;
        }
        else
        {
            bipedLeftLeg = bootLeftAnchor;
            bipedRightLeg = bootRightAnchor;
        }
    }

}

