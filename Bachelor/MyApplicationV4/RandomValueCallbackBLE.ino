#include <CurieBLE.h>

BLEService GMService("19B10000-E8F2-537E-4F6C-D104768A1214");
BLEFloatCharacteristic randomReturn("19B10001-E8F2-537E-4F6C-D104768A1214", BLENotify | BLERead);
BLECharCharacteristic randomTrigger("19B10002-E8F2-537E-4F6C-D104768A1214", BLEWrite);

void setup() {
  Serial.begin(9600);
  
  BLE.begin();

  BLE.setLocalName("Fake_GlucoseMeter");
  BLE.setAdvertisedService(GMService);
  GMService.addCharacteristic(randomReturn);
  GMService.addCharacteristic(randomTrigger);
  
  // add service
  BLE.addService(GMService);

  BLE.setEventHandler(BLEConnected, blePeripheralConnectHandler);
  BLE.setEventHandler(BLEDisconnected, blePeripheralDisconnectHandler);

  // assign event handlers for characteristic
  randomTrigger.setEventHandler(BLEWritten, randomReturnRead);
  randomReturn.setValue(0);

  // start advertising
  BLE.advertise();

  Serial.println(("Bluetooth device active, waiting for connections..."));
}

void loop() {
  BLE.poll();
}

void blePeripheralConnectHandler(BLEDevice central) {
  Serial.print("Connected event, central: ");
  Serial.println(central.address());
}

void blePeripheralDisconnectHandler(BLEDevice central) {
  Serial.print("Disconnected event, central: ");
  Serial.println(central.address());
}

void randomReturnRead(BLEDevice central, BLECharacteristic characteristic) {
  Serial.println("Characteristic called");

  if(randomTrigger.value() == 'r') {
    Serial.print("Sending random value ");
    float randNumber = (float)((random(1, 151) / 10.0) + 1.5);
    Serial.println(randNumber);
    randomReturn.setValue(randNumber);
  }
}

