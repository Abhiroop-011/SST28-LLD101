# Ex7 — ISP: Smart Classroom Devices Interface

## 1. Context
A smart classroom controller manages devices: projector, lights, AC, attendance scanner.

## 2. Current behavior
- There is one large interface `SmartClassroomDevice` containing many methods
- Each device implements methods it does not need using dummy logic
- Controller calls only some methods depending on device type

## 3. What’s wrong (at least 5 issues)
1. Fat interface forces irrelevant methods on devices.
2. Dummy implementations hide bugs and create misleading behavior.
3. Controller is tempted to call methods that some devices don’t meaningfully support.
4. Adding a new device forces implementing many unrelated methods.
5. Device capabilities are unclear; interface does not model reality.

## 4. Your task
- Split the fat interface into smaller capability-based interfaces.
- Update controller and devices to depend only on what they use.
- Preserve console output.

## 5. Constraints
- Preserve output for `Main`.
- Keep device class names unchanged.
- No external libs.

## 6. Acceptance criteria
- No device implements methods irrelevant to it.
- Controller depends only on specific capability interfaces.

## 7. How to run
```bash
cd SOLID/Ex7/src
javac *.java
java Main
```

## 8. Sample output
```text
=== Smart Classroom ===
Projector ON (HDMI-1)
Lights set to 60%
AC set to 24C
Attendance scanned: present=3
Shutdown sequence:
Projector OFF
Lights OFF
AC OFF
```

## 9. Hints (OOP-only)
- Capabilities: power control, brightness control, temperature control, scanning.
- Keep composition: registry can return devices by capability rather than by concrete class.

## 10. Stretch goals
- Add a “smart board” device without implementing unrelated methods.



My Implementation - In the given design, It looks organized because everything inherits from one base type. But when we look at the concrete classes, it is a complete mess.

Because the interface is so 'fat', every device is forced to implement methods it doesn't support or require . For example, the AirConditioner is forced to implement setBrightness(), and the AttendanceScanner is forced to implement setTemperatureC(). The developers just left these methods empty or added dummy logic returning zero."

Misleading Contracts: The AttendanceScanner technically has a setTemperature() method. If a developer calls that method, the compiler won't stop them, but the system won't do anything. It hides bugs.

Fragility: If we add a new SmartBoard that requires a draw() method, we have to add draw() to the giant interface. This forces us to recompile the AirConditioner and add a dummy draw() method to it!

This design violently breaks the Interface Segregation Principle (ISP). ISP states that clients should not be forced to depend on interfaces they do not use

Step 1: Splitting Interfaces. I deleted SmartClassroomDevice and created five small interfaces: PowerControl, BrightnessControl, TemperatureControl, InputControl, and Scanner.

Step 2: Implementing Roles. Now, the devices only implement what they can actually do. The AirConditioner implements PowerControl and TemperatureControl. The AttendanceScanner only implements Scanner, because it never actually gets powered off. There is no more dummy code anywhere.

Step 3: Refactoring the Registry & Controller. I updated the DeviceRegistry to use Java Generics. Now, instead of asking for a device by a hardcoded string name, the ClassroomController asks the registry for a capability.

For example, during startClass(), it just asks for reg.getDevice(BrightnessControl.class). It doesn't care if that device is a LightsPanel or a SmartWindow. During endClass(), it asks the registry for getAllDevices(PowerControl.class) and simply loops through them to power them off."


True ISP Compliance: No class implements methods it doesn't need.

High Cohesion: Interfaces are small, focused, and represent a single role.

Open/Closed Principle: If we want to add a SmartBoard, we just create a new class, implement InputControl and BrightnessControl, and drop it into the registry. We don't have to touch a single existing class or interface.