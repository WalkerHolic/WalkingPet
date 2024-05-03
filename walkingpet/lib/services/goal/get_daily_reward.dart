// 일일 목표 달성시 서버에 보상 요청하는 코드
import 'package:http/http.dart' as http;

Future<bool> sendRewardRequest(int goalSteps) async {
  var response = await http.get(
      Uri.parse('https://walkingpet.co.kr/goal/reward?goalStep=$goalSteps'));
  if (response.statusCode == 200) {
    print(response.body);
    // 요청이 성공적으로 처리된 경우
    return true;
  } else {
    print("에러났어여 : ${response.statusCode}");
    return false;
  }
}
