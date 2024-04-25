import 'package:flutter/material.dart';
import 'package:walkingpet/battle/battle.dart';
import 'package:walkingpet/character/characterinfo.dart';
import 'package:walkingpet/gacha/gacha.dart';
import 'package:walkingpet/goal/goal.dart';
import 'package:walkingpet/group/group.dart';
import 'package:walkingpet/home/home.dart';
import 'package:walkingpet/login/login.dart';
import 'package:walkingpet/ranking/ranking.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Waking Pet',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        fontFamily: 'Roboto', // 글꼴 전역 설정
      ),
      initialRoute: '/login',
      routes: {
        '/login': (context) => const Login(),
        '/battle': (context) => const Battle(),
        '/characterInfo': (context) => const CharacterInfo(),
        '/gacha': (context) => const Gacha(),
        '/goal': (context) => const Goal(),
        '/group': (context) => const Group(),
        '/ranking': (context) => const Ranking(),
        '/home': (context) => const Home(),
      },
    );
  }
}
