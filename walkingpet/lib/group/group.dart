import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:nes_ui/nes_ui.dart';
import 'package:walkingpet/common/bottom_nav_bar.dart';
import 'package:walkingpet/group/widgets/my_group_list.dart';
import 'package:walkingpet/group/widgets/search_group.dart';

class Group extends StatelessWidget {
  const Group({super.key});

  @override
  Widget build(BuildContext context) {
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;
    return Scaffold(
      body: Stack(
        children: <Widget>[
          Positioned.fill(
            child: Image.asset(
              'assets/backgrounds/group.png',
              fit: BoxFit.cover,
            ),
          ),
          Positioned.fill(
            child: Container(
              color: Colors.white.withOpacity(0.6),
            ),
          ),
          Center(
            child: Container(
              width: screenWidth * 0.9,
              height: screenHeight * 0.7,
              decoration: BoxDecoration(
                  color: const Color(0xffffe6b6).withOpacity(0.9),
                  borderRadius: BorderRadius.circular(20)),
              // child: const Column(
              //   children: [
              //     GroupCard(groupName: "야호"),
              //   ],
              // ), //컨테이너 안 child
            ),
          ),
          Positioned(
            top: screenHeight * 0.09,
            left: screenWidth * 0.05,
            child: Material(
              color: Colors.transparent,
              child: DefaultTextStyle(
                style: const TextStyle(
                  fontSize: 25,
                  color: Colors.black,
                  fontFamily: "DungGeunMo",
                ),
                child: SizedBox(
                  width: screenWidth * 0.9,
                  height: screenHeight * 0.73,
                  child: const NesTabView(
                    tabs: [
                      NesTabItem(
                        child: MyGroup(),
                        label: "내 그룹",
                      ),
                      NesTabItem(
                        child: SearchGroup(),
                        label: "그룹 검색",
                      ),
                    ],
                  ),
                ),
              ),
            ),
          )
        ],
      ),
      bottomNavigationBar: const BottomNavBar(
        selectedIndex: 4,
      ),
    );
  }
}
