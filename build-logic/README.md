# Convention Plugins

The `build-logic` folder defines project-specific convention plugins, used to keep a single
source of truth for common module configurations.

This approach is heavily based on
[https://github.com/android/nowinandroid](https://github.com/android/nowinandroid)

Jacoco 테스트 코드 Coverage 실행 방법
우측 상단 Gradle > 모듈 > Tasks > other > create{VariantName}CombinedCoverageReport 실행
e.g) core > domain > Tasks > other > createDebugCombinedCoverageReport

실행 결과 확인
projectRoot > 모듈 > build > reports > jacoco > create{VariantName}CombinedCoverageReport > html > index.html
폴더로 접근하여 index.html 실행