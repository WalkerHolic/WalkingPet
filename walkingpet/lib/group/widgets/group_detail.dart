import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:walkingpet/common/bottom_nav_bar.dart';
import 'package:walkingpet/group/widgets/member_scrollable_list.dart';
import 'package:walkingpet/services/group/get_group_detail.dart';

class GroupDetail extends StatefulWidget {
  final int groupId; //팀 이름

  const GroupDetail({
    super.key,
    required this.groupId,
  });

  @override
  State<GroupDetail> createState() => _GroupDetailState();
}

class _GroupDetailState extends State<GroupDetail> {
  //nullable 타입의 데이터 선언
  Map<String, dynamic>? groupData;
  //null인지 상태 체크해야 함
  bool isLoading = true;
  @override
  void initState() {
    super.initState();
    _fetchGroupDetail();
  }

  Future<void> _fetchGroupDetail() async {
    try {
      final data = await getGroupDetail(widget.groupId);
      setState(() {
        groupData = data; // 데이터를 가져와 상태 변수에 저장
        isLoading = false; //로딩 완료
      });
    } catch (e) {
      setState(() {
        isLoading = false; // 에러 발생 시 로딩 중지
      });
      print("데이터 가져오기 에러 : $e");
    }
  }

  @override
  Widget build(BuildContext context) {
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;

    // 로딩 중이면 스피너
    if (isLoading) {
      return const Scaffold(
          body: Center(
        child: CircularProgressIndicator(),
      ));
    }

    if (groupData == null) {
      return const Scaffold(
          body: Center(
        child: Text("그룹 정보를 가져올 수 없습니다"),
      ));
    }

    int goalStep = 10000; // 목표 걸음수
    int currStep = 3000; // 현재 결음수
    String groupName = groupData!['teamName'] ?? '그룹 이름 없음';
    String description = groupData!['explain'] ?? '설명 없음';

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
              height: screenHeight * 0.8,
              decoration: BoxDecoration(
                  color: const Color(0xfffff3dc).withOpacity(0.9),
                  borderRadius: BorderRadius.circular(20)),
              child: Padding(
                padding:
                    const EdgeInsets.symmetric(vertical: 20, horizontal: 10),
                child: Column(
                  children: <Widget>[
                    Text(
                      groupName,
                      style: const TextStyle(fontSize: 33),
                    ),
                    const SizedBox(height: 10),
                    const Text(
                      "그룹의 홈",
                      style: TextStyle(fontSize: 20),
                    ),
                    Padding(
                      padding: const EdgeInsets.symmetric(vertical: 20),
                      child: Container(
                        width: screenWidth * 0.8,
                        height: screenHeight * 0.1,
                        decoration: BoxDecoration(
                          color: Colors.white.withOpacity(0.7),
                          borderRadius: BorderRadius.circular(20),
                        ),
                        child: Column(
                          //뒤에 배경 깔기위해... container로 묶으려고
                          children: [
                            const Text(
                              "함께 10000보 걷기",
                              style: TextStyle(fontSize: 25),
                            ),
                            Stack(
                              alignment: Alignment.center,
                              // 프로그래스 바 위에 걸음 수를 띄우기 위해
                              children: <Widget>[
                                SizedBox(
                                  // 선형 프로그래스 바의 크기 조절을 위해
                                  width: 250,
                                  child: LinearProgressIndicator(
                                    // 그룹 목표
                                    value: currStep / goalStep,
                                    minHeight: 20,
                                    color: const Color(0xff7DF39D),
                                    backgroundColor: const Color(0xff43695c),
                                  ),
                                ),
                                Text(
                                  "$currStep / $goalStep",
                                  style: const TextStyle(
                                    color: Colors.white,
                                    fontSize: 25,
                                  ),
                                )
                              ],
                            ),
                          ],
                        ),
                      ),
                    ),
                    const Text(
                      "그룹원 목록",
                      style: TextStyle(
                        fontSize: 25,
                      ),
                    ),
                    const SizedBox(
                      height: 20,
                    ),
                    MemberScrollableList(),
                    ElevatedButton(
                        onPressed: () {}, child: const Text("그룹 나가기"))
                  ],
                ),
              ),
            ),
          ),
          Positioned(
            left: screenWidth * 0.9 / 2 - 50,
            top: screenHeight * 0.35,
            child: Image.asset(
              'assets/items/stars.png',
              width: 150,
            ),
          ),
        ],
      ),
      bottomNavigationBar: const BottomNavBar(
        selectedIndex: 4,
      ),
    );
  }
}
