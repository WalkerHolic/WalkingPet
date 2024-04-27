import 'package:flutter/material.dart';
import 'package:walkingpet/ranking/widgets/rank.dart';
import 'package:walkingpet/ranking/widgets/top1to3.dart';

class PersonalRanking extends StatelessWidget {
  const PersonalRanking({super.key});

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          // 1. 어제 / 실시간 / 누적 선택
          const Row(
            mainAxisAlignment: MainAxisAlignment.end,
            children: [
              Text(
                '어제 | 실시간 | 누적',
                textAlign: TextAlign.right,
              ),
              SizedBox(
                width: 15,
              )
            ],
          ),

          const SizedBox(
            height: 10,
          ),

          // 2. 1~3위 표시
          const Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Top1to3(
                ranking: '2',
                characterId: '13',
                nickname: '이겜재밌냐',
                step: '226,254',
              ),
              Top1to3(
                ranking: '1',
                characterId: '13',
                nickname: '1등 야호',
                step: '362,254',
              ),
              Top1to3(
                ranking: '3',
                characterId: '13',
                nickname: '나는지은',
                step: '160,254',
              ),
            ],
          ),

          // 3. 유저의 랭킹 표시
          const Padding(
            padding: EdgeInsets.symmetric(vertical: 20),
            child: Rank(
              ranking: '897',
              characterId: '1',
              nickname: '하이빅싸피',
              step: '226,254',
            ),
          ),

          // 4. 4~10위 표시
          Container(
            // 4-1. 영역 표시
            decoration: BoxDecoration(
              color: const Color.fromARGB(255, 255, 255, 255).withOpacity(0.8),
              borderRadius: BorderRadius.circular(5),
            ),
            margin: const EdgeInsets.symmetric(horizontal: 7),

            child: const Column(
              children: [
                // 4-2. Top 10 텍스트
                Text(
                  'Top 10',
                  style: TextStyle(fontSize: 30),
                ),

                // 4-3. 내용 (스크롤 가능)
                SizedBox(
                  height: 230,
                  child: SingleChildScrollView(
                    scrollDirection: Axis.vertical,
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Rank(
                          ranking: '1',
                          characterId: '13',
                          nickname: '1등 야호',
                          step: '362,254',
                        ),
                        Rank(
                          ranking: '2',
                          characterId: '13',
                          nickname: '이겜재밌냐',
                          step: '226,254',
                        ),
                        Rank(
                          ranking: '3',
                          characterId: '13',
                          nickname: '나는지은',
                          step: '160,254',
                        ),
                        Rank(
                          ranking: '4',
                          characterId: '1',
                          nickname: '룰루루4등잼',
                          step: '8,254',
                        ),
                        Rank(
                          ranking: '5',
                          characterId: '1',
                          nickname: '우희힇',
                          step: '7,254',
                        ),
                        Rank(
                          ranking: '6',
                          characterId: '1',
                          nickname: '6등 울렐레',
                          step: '6,254',
                        ),
                        Rank(
                          ranking: '7',
                          characterId: '1',
                          nickname: '7등임당',
                          step: '5,254',
                        ),
                        Rank(
                          ranking: '8',
                          characterId: '1',
                          nickname: '난 8등',
                          step: '3,254',
                        ),
                        Rank(
                          ranking: '9',
                          characterId: '1',
                          nickname: '구구구구구',
                          step: '2,254',
                        ),
                        Rank(
                          ranking: '10',
                          characterId: '1',
                          nickname: '10등이군',
                          step: '1,254',
                        ),
                      ],
                    ),
                  ),
                ),
              ],
            ),
          ),

          // 5. 아래 빈 공간
          const SizedBox(
            height: 50,
          )
        ],
      ),
    );
  }
}
