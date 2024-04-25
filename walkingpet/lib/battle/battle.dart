import 'package:flutter/material.dart';
import 'package:walkingpet/common/bottom_nav_bar.dart';

class Battle extends StatelessWidget {
  const Battle({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('배틀 Page'),
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: () {
            Navigator.pushNamed(context, '/home');
          },
          child: const Text('Go Back to Home Page'),
        ),
      ),
      bottomNavigationBar: const BottomNavBar(
        selectedIndex: 3,
      ),
    );
  }
}
