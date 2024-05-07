import 'dart:convert';
import '../Interceptor.dart';

const String baseUrl = "https://walkingpet.co.kr";
const String statUrl = "character/stat";

// 캐릭터 스탯 업데이트
Future<Map<String, dynamic>> updateStat(
    int userCharacterId, String value) async {
  final client = AuthInterceptor();
  final url = Uri.parse(
      '$baseUrl/$statUrl?userCharacterId=$userCharacterId&value=$value');

  final response = await client.get(url);

  if (response.statusCode == 200) {
    var data = utf8.decode(response.bodyBytes);
    var jsonData = jsonDecode(data);
    // print(jsonData);
    return jsonData;
    // } else if (response.statusCode == 404) {
    //   print("유저의 해당 캐릭터를 찾을 수 없습니다.");
    // } else if (response.statusCode == 403) {
    //   print("유저의 해당 캐릭터 스탯 포인트가 부족합니다.");
  } else {
    print("캐릭터 능력치 업데이트 받아오기 오류 : ${response.statusCode}");
    throw Error();
  }
}
