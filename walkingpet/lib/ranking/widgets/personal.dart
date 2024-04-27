import 'package:flutter/material.dart';
import 'package:walkingpet/ranking/widgets/rank.dart';
import 'package:walkingpet/ranking/widgets/top1to3.dart';

class PersonalRanking extends StatelessWidget {
  const PersonalRanking({super.key});

  @override
  Widget build(BuildContext context) {
    return const Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          // 1. 어제 / 실시간 / 누적 선택
          Row(
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

          SizedBox(
            height: 10,
          ),

          // 2. 1~3위 표시
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Top1to3(
                ranking: '1',
                characterId: '13',
                nickname: '1등 야호',
                step: '362,254',
              ),
              Top1to3(
                ranking: '2',
                characterId: '13',
                nickname: '이겜재밌냐',
                step: '226,254',
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
          Padding(
            padding: EdgeInsets.symmetric(vertical: 20),
            child: Rank(
              ranking: '897',
              characterId: '1',
              nickname: '하이빅싸피야',
              step: '226,254',
            ),
          ),

          // 4. 4~10위 표시
          Text(
            'Top 10',
            style: TextStyle(fontSize: 30),
          ),

          Column(
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
                step: '6,254',
              ),
              Rank(
                ranking: '5',
                characterId: '1',
                nickname: '우희힇',
                step: '1,254',
              ),
            ],
          ),

          // 5. 아래 빈 공간
          SizedBox(
            height: 50,
          )
        ],
      ),
    );
  }
}
