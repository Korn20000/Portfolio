package com.example.admin.myapplication;

import java.util.UUID;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class GattAttributes {
    public static UUID BLE_WRITE_R_GET_RANDOM_VALUE = UUID.fromString("19B10002-E8F2-537E-4F6C-D104768A1214");
    public static UUID BLE_NOTIFY_RANDOM_VALUE = UUID.fromString("19B10001-E8F2-537E-4F6C-D104768A1214");
    public static UUID BLE_GLUCOSE_METER_SERVICE = UUID.fromString("19B10000-E8F2-537E-4F6C-D104768A1214");
    public static UUID NOTIFY_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
}
