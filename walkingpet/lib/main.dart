import 'package:flutter/material.dart';
import 'package:nes_ui/nes_ui.dart';
import 'package:walkingpet/battle/battleready.dart';
import 'package:walkingpet/character/characterinfo.dart';
import 'package:walkingpet/gacha/gacha.dart';
import 'package:walkingpet/goal/goal.dart';
import 'package:walkingpet/group/group.dart';
import 'package:walkingpet/home/home.dart';
import 'package:walkingpet/levelup/levelup.dart';
import 'package:walkingpet/login/login.dart';
import 'package:walkingpet/ranking/ranking.dart';
import 'package:flutter/services.dart'; // SystemChrome을 사용하기 위해 필요
import 'package:permission_handler/permission_handler.dart';
import 'package:provider/provider.dart';
import 'package:walkingpet/providers/gachabox_count_provider.dart';
import 'package:kakao_flutter_sdk_common/kakao_flutter_sdk_common.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();

  /* 상단바, 하단바 모두 표시 & 상단바 투명하게 */
  SystemChrome.setSystemUIOverlayStyle(const SystemUiOverlayStyle(
    statusBarColor: Colors.transparent, // 상단바 배경을 투명하게
    statusBarIconBrightness: Brightness.dark, // 상단바 아이콘을 어둡게 (라이트 모드)
  ));

  /* 세로 방향 고정 */
  SystemChrome.setPreferredOrientations([
    DeviceOrientation.portraitUp, // 세로 방향으로 위를 가리킴
    DeviceOrientation.portraitDown // 세로 방향으로 아래를 가리킴
  ]);

  KakaoSdk.init(
    nativeAppKey: '779b15a502d5db79f97478c1a0076650',
    javaScriptAppKey: '98dfecd4151782eef7342a07e95b9c57',
  );

  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(
            create: (context) => BoxCounterProvider()..initializeBoxCounts()),
      ],
      child: const MyApp(),
    ),
  );
  //SystemChrome.setEnabledSystemUIMode(SystemUiMode.immersive);
  // SystemChannels.navigation.setMethodCallHandler((MethodCall call) async {
  //   if (call.method == 'popRoute') {
  //     return Future.value(false); // 뒤로 가기 이벤트를 무시합니다.
  //   }
  //   return Future.value(true);
  // }

  _requestPermissions();
}

// 권한 요청 메소드 정의
void _requestPermissions() async {
  // ACTIVITY_RECOGNITION 권한 요청
  var status = await Permission.activityRecognition.request();
  if (status.isGranted) {
  } else {
    // 사용자가 권한을 거부한 경우 처리할 로직 추가 가능
    SystemNavigator.pop();
  }
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    // 1. 기본 테마 가져오기
    final ThemeData baseTheme = flutterNesTheme();

    // 2. TextTheme 생성하고, 모든 텍스트 스타일에 DungGeunMo 글꼴 적용!
    final TextTheme textTheme = baseTheme.textTheme.copyWith(
      titleLarge:
          baseTheme.textTheme.titleLarge?.copyWith(fontFamily: 'DungGeunMo'),
      titleMedium:
          baseTheme.textTheme.titleMedium?.copyWith(fontFamily: 'DungGeunMo'),
      titleSmall:
          baseTheme.textTheme.titleSmall?.copyWith(fontFamily: 'DungGeunMo'),
      headlineLarge:
          baseTheme.textTheme.headlineLarge?.copyWith(fontFamily: 'DungGeunMo'),
      headlineMedium: baseTheme.textTheme.headlineMedium
          ?.copyWith(fontFamily: 'DungGeunMo'),
      headlineSmall:
          baseTheme.textTheme.headlineSmall?.copyWith(fontFamily: 'DungGeunMo'),
      bodyLarge:
          baseTheme.textTheme.bodyLarge?.copyWith(fontFamily: 'DungGeunMo'),
      bodyMedium:
          baseTheme.textTheme.bodyMedium?.copyWith(fontFamily: 'DungGeunMo'),
      bodySmall:
          baseTheme.textTheme.bodySmall?.copyWith(fontFamily: 'DungGeunMo'),
      labelLarge:
          baseTheme.textTheme.labelLarge?.copyWith(fontFamily: 'DungGeunMo'),
      labelMedium:
          baseTheme.textTheme.labelMedium?.copyWith(fontFamily: 'DungGeunMo'),
      labelSmall:
          baseTheme.textTheme.labelSmall?.copyWith(fontFamily: 'DungGeunMo'),
      displayLarge:
          baseTheme.textTheme.displayLarge?.copyWith(fontFamily: 'DungGeunMo'),
    );

    // 3. 수정된 TextTheme + 기존 테마 (결합) => 새 테마 생성
    final ThemeData newTheme = baseTheme.copyWith(
      textTheme: textTheme,
    );

    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Waking Pet',
      theme: newTheme,
      initialRoute: '/login',
      routes: {
        '/login': (context) => const Login(),
        '/home': (context) => const Home(),
        '/goal': (context) => const Goal(),
        '/ranking': (context) => const Ranking(),
        '/characterinfo': (context) => const CharacterInfo(),
        '/gacha': (context) => const Gacha(),
        '/battleready': (context) => const BattleReady(),
        '/group': (context) => const Group(),
        '/levelup': (context) => const LevelUp(),
      },
    );
  }
}
