<h1 align="center">Pokedex Multiplatform</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
   <a href="https://github.com/ujizin"><img alt="Profile" src="https://badgen.net/badge/ujizin/Github/orange?icon=github"/></a>
</p>
<p align="center">
Pokedex KMP is a Kotlin Multiplatform project targeting Android and iOS platforms, built with modern libraries such as Jetpack Compose, Flow, Paging, Room, and more.
</p>

<div style="display:flex; justify-content: space-around; flex-wrap: wrap-reverse">
  <div style="flex: 1; flex-basis: 350px">

## 📚 Libraries

- **Jetpack Compose**: A modern toolkit for building native UIs with a declarative approach, simplifying UI development.
- **Flow**: A Kotlin library for handling asynchronous data streams, offering reactive programming capabilities.
- **Paging**: Manages loading and displaying large data sets with efficient pagination, ensuring smooth user experiences.
- **Room**: An SQLite ORM library that provides an abstraction layer over SQLite for local database management.
- **Ktor/Ktorfit**: A framework for building asynchronous servers and clients in connected systems using Kotlin.
- **Koin**: A pragmatic lightweight dependency injection framework for Kotlin, making it easier to manage dependencies.

## 🏗️ Architecture

The project is structured around the Model-View-ViewModel (MVVM) architecture and employs the repository pattern recommended by Google. For further details, see the [official documentation on Android architecture](https://developer.android.com/topic/architecture).
  </div>
  <div style="flex: 1; display: flex; justify-content: center; flex-basis: 350px">
    <img src="assets/pokedex_android.gif" width="280" height="620"/>
  </div>
</div>

## 📋 License

```
Copyright 2024 ujizin (Lucas Yuji) 

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```