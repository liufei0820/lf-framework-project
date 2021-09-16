package com.lf.rpc.client.protocol;

import com.lf.rpc.client.entity.User;
import com.lf.rpc.util.ByteConverter;

import java.io.*;

/**
 * @Classname RpcProtocol
 * @Date 2021/9/16 下午1:41
 * @Created by fei.liu
 */
public class RpcProtocol implements Serializable {

    public static int CMD_CREATE_USER = 1;
    private int version;
    private int cmd;
    private int magicNum;
    private int bodyLen = 0;
    private byte[] body;
    final public static int HEAD_LEN = 16;

    public byte[] generateByteArray() {
        byte[] data = new byte[HEAD_LEN + bodyLen];
        int index = 0;
        System.arraycopy(ByteConverter.intToBytes(version), 0, data, index, Integer.BYTES);
        index += Integer.BYTES;
        System.arraycopy(ByteConverter.intToBytes(cmd), 0, data, index, Integer.BYTES);
        index += Integer.BYTES;
        System.arraycopy(ByteConverter.intToBytes(magicNum), 0, data, index, Integer.BYTES);
        index += Integer.BYTES;
        System.arraycopy(ByteConverter.intToBytes(bodyLen), 0, data, index, Integer.BYTES);
        index += Integer.BYTES;
        System.arraycopy(body, 0, data, index, body.length);
        return data;
    }

    public RpcProtocol byteArrayToRpcHeader(byte[] data) {
        int index = 0;
        this.setVersion(ByteConverter.bytesToInt(data, index));
        index += Integer.BYTES;

        this.setCmd(ByteConverter.bytesToInt(data, index));
        index += Integer.BYTES;

        this.setMagicNum(ByteConverter.bytesToInt(data, index));
        index += Integer.BYTES;

        this.setBodyLen(ByteConverter.bytesToInt(data, index));
        index += Integer.BYTES;

        this.body = new byte[this.bodyLen];
        System.arraycopy(data, index, this.body, 0, this.bodyLen);

        return this;
    }

    public User byteArrayToUserInfo(byte[] data) {
        User user = new User();
        int index = 0;

        user.setUid(ByteConverter.bytesToLong(data, index));
        index += Long.BYTES;

        user.setAge(ByteConverter.bytesToShort(data, index));
        index += Short.BYTES;

        user.setSex(ByteConverter.bytesToShort(data, index));
        index += Short.BYTES;

        return user;
    }

    public byte[] userInfoToByteArray(User info) {
        byte[] data = new byte[Long.BYTES + Short.BYTES + Short.BYTES];
        int index = 0;
        System.arraycopy(ByteConverter.longToBytes(info.getUid()), 0, data, index, Long.BYTES);
        index += Long.BYTES;

        System.arraycopy(ByteConverter.shortToBytes(info.getAge()), 0, data, index, Short.BYTES);
        index += Short.BYTES;

        System.arraycopy(ByteConverter.shortToBytes(info.getSex()), 0, data, index, Short.BYTES);
        return data;
    }

    public static Object bytes2Object(byte[] objBytes) throws Exception {
        if (objBytes == null || objBytes.length == 0) {
            return null;
        }

        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        Object obj = oi.readObject();
        bi.close();
        oi.close();
        return obj;
    }

    public static byte[] object2Bytes(Serializable object) throws Exception {
        if (object == null) {
            return null;
        }

        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(object);
        oo.close();
        bo.close();
        return bo.toByteArray();
    }

    public byte[] createUserRespToByteArray(int result) {
        byte[] data = new byte[Integer.BYTES];
        int index = 0;
        System.arraycopy(ByteConverter.intToBytes(result), 0, data, index, Integer.BYTES);
        return data;
    }


    public static int getCmdCreateUser() {
        return CMD_CREATE_USER;
    }

    public static void setCmdCreateUser(int cmdCreateUser) {
        CMD_CREATE_USER = cmdCreateUser;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public int getMagicNum() {
        return magicNum;
    }

    public void setMagicNum(int magicNum) {
        this.magicNum = magicNum;
    }

    public int getBodyLen() {
        return bodyLen;
    }

    public void setBodyLen(int bodyLen) {
        this.bodyLen = bodyLen;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
