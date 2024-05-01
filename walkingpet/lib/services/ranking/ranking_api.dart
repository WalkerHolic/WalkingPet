import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:walkingpet/ranking/widgets/rank.dart';

class RankingApiService {
  static const String baseUrl = "walkingpet.co.kr";
  static const String yesterdayTop10 = "ranking/person/top10?value=yesterday";

  static Future<List<Rank>> getPersonTop10Yesterday() async {
    List<Rank> rankingPersonTop10Yesterday = [];
    final url = Uri.parse('$baseUrl/$yesterdayTop10');
    final response = await http.get(url);

    if (response.statusCode == 200) {
      final List<dynamic> ranks = jsonDecode(response.body);
      for (var rank in ranks) {
        rankingPersonTop10Yesterday.add(Rank.fromJson(rank));
        // final rankPersonTop10Yesterday = Rank.fromJson(rank);
        // print(rankPersonTop10Yesterday);
      }
      print(rankingPersonTop10Yesterday);
      return rankingPersonTop10Yesterday;
    }
    throw Error();
  }
}
