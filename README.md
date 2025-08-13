# JumpHelper - 微信跳一跳辅助工具 🎮

![License](https://img.shields.io/badge/license-MIT-blue.svg) ![Platform](https://img.shields.io/badge/platform-Android-brightgreen.svg)

一款简单、轻量、免 Root 的微信跳一跳辅助工具，基于 Android 无障碍服务实现长按操作，支持自定义参数与界面主题，适用于日常娱乐与直播场景。

> ⚠️ 本项目仅供学习交流使用，请勿用于非法用途。游戏辅助可能违反平台规则，请谨慎使用。

---

## ✅ 功能简介

- **智能跳跃**：通过手动绘制两点连线，自动计算跳跃距离，调节长按时间完成跳跃。
- **免 Root**：完全基于 Android 无障碍服务（Accessibility Service）实现，无需获取 Root 权限。
- **悬浮窗控制**：支持开启/隐藏悬浮窗，直播时可隐藏界面保持画面干净。
- **高自定义性**：
  - 自定义长按屏幕位置
  - 调整跳跃“速度值”（模拟物理公式：路程 = 速度 × 时间）
  - 精确控制跳跃时长，提升准确率
- **主题美化**：
  - 自定义悬浮窗透明度
  - 可设置连线颜色、界面风格等，打造个性化操作体验

---

## 🚀 使用方法

1. 下载并安装 APK（见 [Releases](https://github.com/yourusername/JumpHelper/releases)）
2. 打开应用，授予 **无障碍服务权限**
3. 启动微信跳一跳小游戏
4. 激活悬浮窗，用手指在屏幕上绘制从起点到终点的连线
5. 工具自动计算距离并执行长按跳跃
6. （可选）在设置中调整速度系数、颜色、透明度等参数

---

## 🛠 技术实现

- 核心逻辑：通过监听用户绘制的线段长度，结合自定义“速度值”计算所需长按时间（`时间 = 距离 / 速度`）
- 输入模拟：使用 Android 无障碍服务发送长按事件，模拟手指按压
- 界面交互：通过悬浮窗实现用户绘图与实时反馈

---

## 🔧 第三方依赖

本项目使用了以下优秀的开源库，特此致谢：

- [`EasyWindow`](https://github.com/getActivity/EasyWindow)  
  用于快速实现可拖拽、可隐藏的悬浮窗管理  
  ```gradle
  implementation 'com.github.getActivity:EasyWindow:10.62'
  ```

- [`Toasty`](https://github.com/GrenderG/Toasty)  
  增强版 Toast 提示库，支持多种样式与图标  
  ```gradle
  implementation 'com.github.GrenderG:Toasty:1.5.2'
  ```

- [`android-xcolorpicker`](https://github.com/wuzhendev/android-xcolorpicker)  
  颜色选择器组件，用于自定义绘制线条与界面颜色  
  ```gradle
  implementation 'com.github.wzhendev:android-xcolorpicker:1.0.0'
  ```

---

## 🎨 截图预览

> （建议添加 2-3 张应用界面截图，展示绘图、设置页、主题颜色等）

![截图1](screenshots/screenshot1.png)
![截图2](screenshots/screenshot2.png)

---

## 📄 开源协议

本项目遵循 [MIT License](LICENSE)，欢迎 Fork、Star 与贡献代码！

---

## 📬 联系作者

- QQ：[1228727365](http://wpa.qq.com/msgrd?v=3&uin=1228727365&site=qq&menu=yes)（点击联系）
- Email：your_email@example.com（可选）
- GitHub：[@yourusername](https://github.com/yourusername)

---

## 🙌 致谢

感谢上述开源库的开发者，让开发更高效、体验更出色！

> 温馨提示：合理使用辅助工具，享受技术乐趣的同时尊重游戏规则。
