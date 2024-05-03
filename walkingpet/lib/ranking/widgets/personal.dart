import 'package:flutter/material.dart';
import 'package:walkingpet/ranking/widgets/myrank.dart';
import 'package:walkingpet/ranking/widgets/rank.dart';
import 'package:walkingpet/ranking/widgets/top1to3.dart';
import 'package:walkingpet/services/ranking/personal_yesterday.dart';

class PersonalRanking extends StatefulWidget {
  const PersonalRanking({super.key});

  @override
  State<PersonalRanking> createState() => _PersonalRankingState();
}

class _PersonalRankingState extends State<PersonalRanking> {
  // 필요한 변수 만들기
  List top10 = [];
  Map<String, dynamic> myrank = {};
  List top3 = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    initTop10();
  }

  // API 요청으로 데이터 불러오기
  Future<void> initTop10() async {
    try {
      var responseTop10 = await getTop10();
      var responseMyRank = await getMyRank();
      var responseTop3 = await getTop3();

      setState(() {
        top10 = responseTop10['data']['topRanking'];
        myrank = responseMyRank['data'];
        top3 = responseTop3['data']['topRanking'];
        isLoading = false;
      });
    } catch (e) {
      isLoading = false;
    }
  }

  @override
  Widget build(BuildContext context) {
    return const Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          // 3-1. 개인 / 그룹 / 배틀 선택
          Text(
            '개인랭킹',
            style: TextStyle(fontSize: 50),
          ),
          Text(
            '- 유저확보중 -',
            style: TextStyle(fontSize: 35),
          ),
        ],
      ),

      //   child: Column(
      //     mainAxisAlignment: MainAxisAlignment.center,
      //     crossAxisAlignment: CrossAxisAlignment.center,
      //     children: [
      //       // 1. 어제 / 실시간 / 누적 선택
      //       const Row(
      //         mainAxisAlignment: MainAxisAlignment.end,
      //         children: [
      //           Text(
      //             // '어제 | 실시간 | 누적',
      //             '어제자',
      //             textAlign: TextAlign.right,
      //           ),
      //           SizedBox(
      //             width: 15,
      //           )
      //         ],
      //       ),

      //       const SizedBox(
      //         height: 10,
      //       ),

      //       // 2. 1~3위 표시
      //       Row(
      //         mainAxisAlignment: MainAxisAlignment.center,
      //         children: [
      //           ...top3.map((item) {
      //             return Top1to3(
      //               ranking: item['ranking'],
      //               characterId: item['characterId'],
      //               nickname: item['nickname'],
      //               step: item['step'],
      //             );
      //           }),
      //         ],
      //       ),

      //       // 3. 유저의 랭킹 표시
      //       Padding(
      //         padding: const EdgeInsets.symmetric(vertical: 20),
      //         child: Container(
      //           decoration: BoxDecoration(
      //             color:
      //                 const Color.fromARGB(255, 255, 255, 255).withOpacity(0.8),
      //             borderRadius: BorderRadius.circular(5),
      //           ),
      //           margin: const EdgeInsets.symmetric(horizontal: 7),
      //           child: MyRank(
      //             ranking: myrank['ranking'] as int? ?? 0,
      //             step: myrank['step'] as int? ?? 0,
      //             characterId: myrank['characterId'] as int? ?? 0,
      //             nickname: myrank['nickname'] as String? ?? 'Unknown',
      //           ),
      //         ),
      //       ),

      //       // 4. 4~10위 표시
      //       Container(
      //         // 4-1. 영역 표시
      //         decoration: BoxDecoration(
      //           color: const Color.fromARGB(255, 255, 255, 255).withOpacity(0.8),
      //           borderRadius: BorderRadius.circular(5),
      //         ),
      //         margin: const EdgeInsets.symmetric(horizontal: 7),

      //         child: Column(
      //           children: [
      //             // 4-2. Top 10 텍스트
      //             const Text(
      //               'Top 10',
      //               style: TextStyle(fontSize: 30),
      //             ),

      //             // 4-3. 내용 (스크롤 가능)
      //             SizedBox(
      //               height: 230,
      //               child: SingleChildScrollView(
      //                 scrollDirection: Axis.vertical,
      //                 child: Column(
      //                   mainAxisAlignment: MainAxisAlignment.center,
      //                   children: [
      //                     ...top10.map((item) {
      //                       return Rank(
      //                         ranking: item['ranking'],
      //                         nickname: item['nickname'],
      //                         step: item['step'],
      //                       );
      //                     }),
      //                   ],
      //                 ),
      //               ),
      //             ),
      //           ],
      //         ),
      //       ),
      //     ],
      //   ),
    );
  }
}
