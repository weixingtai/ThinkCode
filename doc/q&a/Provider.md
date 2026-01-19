# Provider

1. **问：** 设备未解锁报错：Failed to find provider info for XXXX (user not unlocked)

   **答：** 对应provider添加：android:directBootAware="true"属性

2. **问：** 设备未解锁报错：SharedPreferences in credential encrypted storage are not available until after user (id 0) is unlocked

   **答：** Application添加：android:defaultToDeviceProtectedStorage="true"属性