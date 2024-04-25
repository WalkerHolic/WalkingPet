import 'package:flutter/material.dart';
import 'package:walkingpet/battle/battle.dart';
import 'package:walkingpet/character/characterinfo.dart';
import 'package:walkingpet/gacha/gacha.dart';
import 'package:walkingpet/goal/goal.dart';
import 'package:walkingpet/group/group.dart';
import 'package:walkingpet/home/home.dart';
import 'package:walkingpet/login/login.dart';
import 'package:walkingpet/ranking/ranking.dart';
import 'package:nes_ui/nes_ui.dart';
import 'package:flutter/services.dart'; // SystemChrome을 사용하기 위해 필요

void main() {
  runApp(const MyApp());
  SystemChrome.setEnabledSystemUIMode(SystemUiMode.immersive);
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Waking Pet',
      theme: ThemeData(),
      initialRoute: '/home',
      routes: {
        '/login': (context) => const Login(),
        '/home': (context) => const Home(),
        '/goal': (context) => const Goal(),
        '/ranking': (context) => const Ranking(),
        '/characterinfo': (context) => const CharacterInfo(),
        '/gacha': (context) => const Gacha(),
        '/battle': (context) => const Battle(),
        '/group': (context) => const Group(),
      },
    );
  }
}
