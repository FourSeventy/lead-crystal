package com.silvergobletgames.leadcrystal.netcode;

import com.silvergobletgames.leadcrystal.core.ExtendedSceneObjectClasses;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.serialize.SerializableSerializer;
import com.silvergobletgames.sylver.core.InputHandler;
import com.silvergobletgames.sylver.core.Scene.Layer;
import com.silvergobletgames.sylver.core.SceneObject.CoreClasses;
import com.silvergobletgames.sylver.graphics.Anchorable.Anchor;
import com.silvergobletgames.sylver.graphics.Color;
import com.silvergobletgames.sylver.graphics.LightEffect.LightEffectType;
import com.silvergobletgames.sylver.graphics.ImageEffect.ImageEffectType;
import com.silvergobletgames.sylver.graphics.Text;
import com.silvergobletgames.sylver.netcode.*;
import com.silvergobletgames.sylver.util.SerializableEntry;
import java.awt.Point;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.*;
import net.phys2d.math.ROVector2f;
import net.phys2d.math.Vector2f;
import com.silvergobletgames.leadcrystal.combat.Damage;
import com.silvergobletgames.leadcrystal.combat.ProcEffect.ProcType;
import com.silvergobletgames.leadcrystal.combat.Stat;
import com.silvergobletgames.leadcrystal.combat.StateEffect.StateEffectType;
import com.silvergobletgames.leadcrystal.core.CursorFactory.CursorType;
import com.silvergobletgames.leadcrystal.core.ExtendedImageAnimations;
import com.silvergobletgames.leadcrystal.core.LeadCrystalTextType;
import com.silvergobletgames.leadcrystal.cutscenes.CutsceneManager.Cutscenes;
import com.silvergobletgames.leadcrystal.entities.Entity.FacingDirection;
import com.silvergobletgames.leadcrystal.entities.EntityEffect.EntityEffectType;
import com.silvergobletgames.leadcrystal.entities.WorldObjectEntity.WorldObjectType;
import com.silvergobletgames.leadcrystal.entities.EntityTooltip.EntityTooltipField;
import com.silvergobletgames.leadcrystal.items.ArmorManager.ArmorID;
import com.silvergobletgames.leadcrystal.items.Potion;
import com.silvergobletgames.leadcrystal.netcode.JoinResponse.ReasonCode;
import com.silvergobletgames.leadcrystal.netcode.OpenInstructionalTipPacket.InstructionalTip;
import com.silvergobletgames.leadcrystal.netcode.OpenMenuPacket.MenuID;
import com.silvergobletgames.leadcrystal.skills.Skill.SkillID;
import com.silvergobletgames.sylver.audio.Sound;
import com.silvergobletgames.sylver.audio.Sound.SoundType;
import com.silvergobletgames.sylver.graphics.AnimationPack.CoreAnimations;
import com.silvergobletgames.sylver.graphics.AnimationPack.ImageAnimation;
import com.silvergobletgames.sylver.graphics.Text.CoreTextType;
import com.silvergobletgames.sylver.graphics.TextEffect.TextEffectType;
import com.silvergobletgames.sylver.util.SylverVector2f;

public class SerializationRegistrator 
{
    
    public static Kryo registerSerialization(Kryo k)
    {
        Kryo kryo = k;
        kryo.register(HoverEntityPacket.class);
        kryo.register(LeadCrystalTextType.class);
        kryo.register(CoreTextType.class);
        kryo.register(HostLevelProgressionAdjust.class);
        kryo.register(InstructionalTip.class);
        kryo.register(CloseInstructionalTipPacket.class);
        kryo.register(OpenInstructionalTipPacket.class);
        kryo.register(PlayerPredictionData.class);
        kryo.register(setSideQuestStatusPacket.class);
        kryo.register(RespawnRequestPacket.class);
        kryo.register(Cutscenes.class);
        kryo.register(SylverVector2f.class);
        kryo.register(TextEffectType.class);
        kryo.register(LevelCompletePacket.class);
        kryo.register(BuySkillPacket.class);
        kryo.register(ClientChatPacket.class);
        kryo.register(CoreAnimations.class);
        kryo.register(ChooseLevelPacket.class);
        kryo.register(ExtendedSceneObjectClasses.class);
        kryo.register(ArmorAdjustPacket.class);
        kryo.register(MenuID.class);
        kryo.register(CloseMenuPacket.class);
        kryo.register(BuyArmorPacket.class);
        kryo.register(ArmorID.class);
        kryo.register(BuyPotionPacket.class);
        kryo.register(OpenMenuPacket.class);
        kryo.register(Layer.class);
        kryo.register(Damage.DamageType.class);
        kryo.register(ArrayList.class);     
        kryo.register(Class.class, new SerializableSerializer());  
        kryo.register(UUID.class,
        new Serializer() 
        {
            public void writeObjectData (ByteBuffer buffer, Object object) 
            {
                    buffer.putLong(((UUID)object).getMostSignificantBits());
                    buffer.putLong(((UUID)object).getLeastSignificantBits());
            }
            public UUID readObjectData (ByteBuffer buffer, Class type) 
            {
                    return new UUID(buffer.getLong(),buffer.getLong());
            }

        });
        kryo.register(Set.class);
        kryo.register(Stat.class);
        kryo.register(Sound.class);
        kryo.register(SoundType.class);
        kryo.register(SoundDataPacket.class);
        kryo.register(Vector2f.class);
        kryo.register(ROVector2f.class);
        kryo.register(Color.class);
        kryo.register(Color[].class);
        kryo.register(Text.TextType.class);     
        kryo.register(HashMap.class);
        kryo.register(Point.class);
        kryo.register(byte[].class);
        kryo.register(InputHandler.class);    
        kryo.register(ClientInputPacket.class);      
        kryo.register(JoinRequest.class);
        kryo.register(JoinResponse.class);
        kryo.register(RenderDataPacket.class);
        kryo.register(TreeMap.class);
        kryo.register(LinkedHashSet.class);
        kryo.register(WorldObjectType.class);
        kryo.register(Vector2f[].class);
        kryo.register(ROVector2f[].class);
        kryo.register(SceneObjectRenderData.class);
        kryo.register(ImageAnimation.class);
        kryo.register(ExtendedImageAnimations.class);
        kryo.register(HashSet.class);
        kryo.register(URL.class,new SerializableSerializer());
        kryo.register(Entry.class);
        kryo.register(SerializableEntry.class);
        kryo.register(SkillDataPacket.class); 
        kryo.register(DisconnectRequest.class);
        kryo.register(SceneObjectRenderDataChanges.class);
        kryo.register(ExtendedSceneObjectClasses.class);
        kryo.register(CoreClasses.class);
        kryo.register(java.lang.Object[].class);
        kryo.register(SizeTest.class);
        kryo.register(SerializableEntry[].class);
        kryo.register(String[].class);
        kryo.register(SceneObjectRenderDataChanges[].class);
        kryo.register(Set.class);
        kryo.register(SkillID.class);
        kryo.register(Potion.class);
        kryo.register(SimpleEntry.class);
        kryo.register(OpenDialoguePacket.class);
        kryo.register(SceneObjectSaveData.class);
        kryo.register(RespawnPacket.class);
        kryo.register(CursorChangePacket.class);
        kryo.register(CursorType.class);
        kryo.register(ChangeLevelPacket.class);
        kryo.register(SaveGamePacket.class);
        kryo.register(ReasonCode.class);
        kryo.register(MovePlayerToPointPacket.class);
        kryo.register(EntityTooltipField.class);
        kryo.register(ClientPacket.class);
        kryo.register(ChatPacket.class);
        kryo.register(ImageEffectType.class);
        kryo.register(StateEffectType.class);
        kryo.register(ProcType.class);
        kryo.register(LightEffectType.class);
        kryo.register(Anchor.class);
        kryo.register(EntityEffectType.class);
        kryo.register(SaveData.class);
        kryo.register(RenderData.class);
        
        return kryo;
        
    }
    
   
}

 
