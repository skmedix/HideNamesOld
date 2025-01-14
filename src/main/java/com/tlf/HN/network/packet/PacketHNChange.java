package com.tlf.HN.network.packet;

import com.tlf.HN.common.HideNames;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHNChange implements IMessage {
	private String username;
	private boolean newState;

	public PacketHNChange() {
	}

	public PacketHNChange(EntityPlayer player, boolean newState) {
		this.username = player.getCommandSenderEntity().getName();
		this.newState = newState;
	}

	public PacketHNChange(String username, boolean newState) {
		this.username = username;
		this.newState = newState;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.username = ByteBufUtils.readUTF8String(buf);
		this.newState = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.username);
		buf.writeBoolean(this.newState);
	}

	public static class Handler implements IMessageHandler<PacketHNChange, IMessage> {
		@Override
		public IMessage onMessage(PacketHNChange message, MessageContext ctx) {
			HideNames.instance.hiddenPlayers.remove(message.username);
			HideNames.instance.hiddenPlayers.put(message.username, message.newState);
			return null; //Reply
		}
	}
}