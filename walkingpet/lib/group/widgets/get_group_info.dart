import 'package:http/http.dart' as http;

void getGroupInfo() async {
  final response =
      await http.get(Uri.parse('https://walkingpet.co.kr/team/belong'));
  if (response.statusCode == 200) {
    print(response.body);
    return;
  } else {
    //상태 코드와 함께 에러 메세지 출력력
    print("에러남 : ${response.statusCode}");
    throw Error();
  }
}
