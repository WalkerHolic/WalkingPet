import 'package:flutter/material.dart';
import 'package:walkingpet/ranking/widgets/rank.dart';
import 'package:walkingpet/ranking/widgets/top1to3.dart';
import 'package:walkingpet/services/ranking/personal.dart';

class PersonalRanking extends StatefulWidget {
  const PersonalRanking({super.key});

  @override
  State<PersonalRanking> createState() => _PersonalRankingState();
}

class _PersonalRankingState extends State<PersonalRanking> {
  // Map<String, dynamic> top10 = {};
  // List<dynamic> top10 = [];
  List top10 = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    initTop10();
  }

  Future<void> initTop10() async {
    try {
      var response = await getTop10();
      setState(() {
        top10 = response['data']['topRanking'];
        isLoading = false;
      });
    } catch (e) {
      isLoading = false;
    }
  }

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
          // 유진이 피드백: 나의 랭킹 897위 (226,254걸음 이런식으로 하면 어떨까?)
          const Padding(
            padding: EdgeInsets.symmetric(vertical: 20),
            // child: Rank(
            //   ranking: '897',
            //   nickname: '하이빅싸피',
            //   step: '1,000',
            // ),
          ),

          // 4. 4~10위 표시
          Container(
            // 4-1. 영역 표시
            decoration: BoxDecoration(
              color: const Color.fromARGB(255, 255, 255, 255).withOpacity(0.8),
              borderRadius: BorderRadius.circular(5),
            ),
            margin: const EdgeInsets.symmetric(horizontal: 7),

            child: Column(
              children: [
                // 4-2. Top 10 텍스트
                const Text(
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
                        ...top10.map((item) {
                          return Rank(
                            ranking: item['ranking'],
                            nickname: item['nickname'],
                            step: item['step'],
                          );
                        }),
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
