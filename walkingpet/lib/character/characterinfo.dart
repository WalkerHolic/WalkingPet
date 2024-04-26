import 'package:flutter/material.dart';
import 'package:walkingpet/common/bottom_nav_bar.dart';

// 캐릭터 정보
class CharacterInfo extends StatelessWidget {
  const CharacterInfo({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            // 1. 유저 닉네임
            const Text(
              '닉네임적어요',
              style: TextStyle(
                // fontWeight: FontWeight.bold,
                fontSize: 35,
              ),
            ),
            const SizedBox(height: 5),
            // 2. 캐릭터 이미지
            Image.asset(
              'assets/animals/cow/cow_walk.gif',
              height: 200,
              scale: 0.3,
            ),

            // 3. 변경 버튼
            // const Text('변경버튼', strutStyle: StrutStyle()),
            ElevatedButton(
              onPressed: () {
                Navigator.pushNamed(context, '/characterinfo');
              },
              child: const Text('변경버튼'),
            ),
            // DottedButton(
            //   onTap: () {
            //     print('도트 버튼 클릭!');
            //   },
            //   label: '클릭',
            //   dotSize: 6.0,
            //   dotColor: Colors.blue,
            //   textColor: Colors.blue,
            //   numberOfDots: 20,
            // ),
            const SizedBox(height: 10),

            // 4. 레벨 & 경험치 바
            const Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text(
                  'Lv. 7',
                  style: TextStyle(
                    fontSize: 25,
                  ),
                ),
                SizedBox(
                  width: 20,
                ),
                SizedBox(
                  width: 200,
                  height: 20,
                  child: ClipRRect(
                    borderRadius: BorderRadius.all(Radius.circular(10)),
                    child: LinearProgressIndicator(
                      value: 0.7,
                      backgroundColor: Colors.grey,
                      valueColor: AlwaysStoppedAnimation<Color>(Colors.green),
                    ),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 10),

            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                // 5. 능력치: 체력, 공격력, 방어력
                const Column(
                  children: [
                    // 5-1. 체력
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Icon(
                          Icons.favorite,
                          color: Colors.red,
                        ),
                        SizedBox(width: 4),
                        Text(
                          ' 체력  17(16+1)',
                          style: TextStyle(
                            fontSize: 20,
                          ),
                        ),
                        Icon(
                          Icons.add_box_sharp,
                          color: Color.fromRGBO(251, 192, 45, 1),
                        ),
                      ],
                    ),
                    SizedBox(height: 5),
                    // 5-2. 공격력
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Icon(
                          Icons.flash_on,
                          color: Colors.yellow,
                        ),
                        SizedBox(width: 4),
                        Text(
                          '공격력 18(16+2)',
                          style: TextStyle(
                            fontSize: 20,
                          ),
                        ),
                        Icon(
                          Icons.add_box_sharp,
                          color: Color.fromRGBO(251, 192, 45, 1),
                        ),
                      ],
                    ),
                    SizedBox(height: 5),
                    // 5-3. 방어력
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Icon(
                          Icons.shield,
                          color: Colors.blue,
                        ),
                        SizedBox(width: 4),
                        Text(
                          '방어력 12(7+5)',
                          style: TextStyle(
                            fontSize: 20,
                          ),
                        ),
                        Icon(
                          Icons.add_box_sharp,
                          color: Color.fromRGBO(251, 192, 45, 1),
                        ),
                      ],
                    ),
                    SizedBox(height: 5),
                  ],
                ),
                const SizedBox(
                  width: 20,
                ),

                // 6. & 7. 남은 능력치 포인트 & 초기화 버튼
                Column(
                  children: [
                    // 6. 남은 능력치 포인트
                    const Text(
                      '남은 포인트',
                      style: TextStyle(
                        fontSize: 15,
                      ),
                    ),
                    const Text(
                      '3',
                      style: TextStyle(
                        fontSize: 30,
                      ),
                    ),
                    // 7. 초기화 버튼
                    ElevatedButton(
                      onPressed: () {
                        Navigator.pushNamed(context, '/characterinfo');
                      },
                      child: const Text('초기화'),
                    ),
                  ],
                ),
              ],
            ),
          ],
        ),
      ),
      bottomNavigationBar: const BottomNavBar(
        selectedIndex: 0,
      ),
    );
  }
}
