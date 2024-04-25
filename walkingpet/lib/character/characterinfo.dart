import 'package:flutter/material.dart';

class CharacterInfo extends StatelessWidget {
  const CharacterInfo({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('캐릭터 Page'),
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: () {
            Navigator.pushNamed(context, '/home');
          },
          child: const Text('Go Back to Home Page'),
        ),
      ),
    );
  }
}
