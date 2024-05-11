import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:walkingpet/ranking/widgets/myrank.dart';
import 'package:walkingpet/ranking/widgets/rank.dart';
import 'package:walkingpet/ranking/widgets/top1to3.dart';
import 'package:walkingpet/services/ranking/personal.dart';

class BattleRanking extends StatefulWidget {
  const BattleRanking({super.key});

  @override
  State<BattleRanking> createState() => _PersonalRankingState();
}

class _PersonalRankingState extends State<BattleRanking> {
  // 필요한 변수 만들기
  List top10 = [];
  Map<String, dynamic> myrank = {};
  List top3 = [];
  bool isLoading = true;
  String selectedTimeFrame = 'realtime';

  @override
  void initState() {
    super.initState();
    fetchData();
  }

  // API 요청으로 데이터 불러오기 => 기본 realtime으로 설정
  Future<void> fetchData({String timeframe = 'realtime'}) async {
    try {
      var responseTop10 = await getTop10(timeframe: timeframe);
      var responseMyRank = await getMyRank(timeframe: timeframe);
      var responseTop3 = await getTop3(timeframe: timeframe);

      setState(() {
        top10 = responseTop10['data']['topRanking'];
        myrank = responseMyRank['data'];
        top3 = responseTop3['data']['topRanking'];
        isLoading = false;
        selectedTimeFrame = timeframe;
      });
    } catch (e) {
      setState(() {
        isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: isLoading
          ? const CircularProgressIndicator() // 로딩 중 인디케이터 추가
          : Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                // 1. 개인랭킹과의 줄맞춤을 위한 영역
                Container(
                  margin: const EdgeInsets.symmetric(vertical: 20),
                  child: const Text('  '),
                ),

                // 2. 1~3위 표시
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    ...top3.map((item) {
                      return Top1to3(
                        ranking: item['ranking'] as int? ?? 0,
                        characterId: item['characterId'] as int? ?? 0,
                        nickname: item['nickname'] as String? ?? 'Unknown',
                        step: item['step'] as int? ?? 0,
                      );
                    }),
                  ],
                ),

                // 3. 유저의 랭킹 표시
                Padding(
                  padding: const EdgeInsets.symmetric(vertical: 20),
                  child: Container(
                    decoration: BoxDecoration(
                      color: const Color.fromARGB(255, 255, 255, 255)
                          .withOpacity(0.8),
                      borderRadius: BorderRadius.circular(5),
                    ),
                    margin: const EdgeInsets.symmetric(horizontal: 7),
                    child: MyRank(
                      ranking: myrank['ranking'] as int? ?? 0,
                      step: myrank['step'] as int? ?? 0,
                      characterId: myrank['characterId'] as int? ?? 0,
                      nickname: myrank['nickname'] as String? ?? 'Unknown',
                    ),
                  ),
                ),

                // 4. 4~10위 표시
                Container(
                  // 4-1. 영역 표시
                  decoration: BoxDecoration(
                    color: const Color.fromARGB(255, 255, 255, 255)
                        .withOpacity(0.8),
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
                                  ranking: item['ranking'] as int? ?? 0,
                                  nickname:
                                      item['nickname'] as String? ?? 'Unknown',
                                  step: item['step'] as int? ?? 0,
                                );
                              }),
                            ],
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
    );
  }
}
