# 架构Q&A

问: MVC, MVP, MVVM 和 MVI 架构的原理和区别？

答: 这四种架构的核心都在于职责分离，主要区别是通信方式和数据流。
MVC 架构 View 与 Model 直接交互，同时Activity/Fragment兼具View和Controller角色，导致 Controller 相对臃肿，耦合重；
MVP 架构 View 与 Model 彻底隔离，View仅负责UI、Presenter处理业务逻辑、Model处理数据，层与层通过接口交互，解决了MVC中Activity职责臃肿的问题，但也导致需要定义大量的接口；
MVVM 架构是基于数据驱动UI，通过ViewModel隔离View和Model，依托LiveData（生命周期感知）、DataBinding（自动更新UI）实现彻底解耦，是Android官方推荐架构，但也有数据流分散、状态难以追踪的缺点；
MVI 架构基于单向数据流，将用户交互封装为Intent、UI状态封装为不可变State，全程数据单向流转，状态可预测、可追溯，是比 MVVM 更严格的响应式架构。