import 'package:flutter/material.dart';

class BottomNavBar extends StatelessWidget {
  final int selectedIndex;

  const BottomNavBar({
    super.key,
    required this.selectedIndex,
  });

  @override
  Widget build(BuildContext context) {
    return BottomNavigationBar(
      currentIndex: selectedIndex,
      backgroundColor: Colors.black, // 배경색을 검정색으로 설정
      unselectedItemColor: Colors.white, // 선택되지 않은 아이템 색상을 흰색으로 설정
      selectedItemColor: Colors.orange, // 선택된 아이템 색상을 주황색으로 설정
      items: const [
        BottomNavigationBarItem(
          icon: Icon(Icons.pets),
          label: '캐릭터',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.star),
          label: '뽑기',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.home),
          label: '홈',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.gamepad),
          label: '배틀',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.pages),
          label: '그룹',
        ),
      ],
      onTap: (index) {
        if (index != selectedIndex) {
          switch (index) {
            case 0:
              Navigator.pushReplacementNamed(context, '/characterinfo');
              break;
            case 1:
              Navigator.pushReplacementNamed(context, '/gacha');
              break;
            case 2:
              Navigator.pushReplacementNamed(context, '/home');
              break;
            case 3:
              Navigator.pushReplacementNamed(context, '/battle');
              break;
            case 4:
              Navigator.pushReplacementNamed(context, '/group');
              break;
          }
        }
      },
    );
  }
}
